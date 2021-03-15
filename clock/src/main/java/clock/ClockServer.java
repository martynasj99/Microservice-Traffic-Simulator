package clock;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.*;
import akka.pattern.Patterns;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import ch.megard.akka.http.cors.javadsl.CorsDirectives;
import clock.messages.Init;
import clock.messages.Registration;
import clock.messages.Result;
import clock.messages.Unregister;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.function.Supplier;

public class ClockServer extends AllDirectives {

    private Duration TIMEOUT = Duration.ofSeconds(5L);

    public static void main(String[] args){
        ActorSystem system = ActorSystem.create("clock-service");
        Http http = Http.get(system);

        ClockServer app = new ClockServer(system);
        Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.createRoute().flow(system);
        http.newServerAt("localhost", 9000).bindFlow(routeFlow);

        System.out.println("Server online at http://localhost:9000/");
    }

    private ActorRef clock;
    private ActorRef broker;

    public ClockServer(ActorSystem system){
        clock = system.actorOf(Clock.props());
        broker = system.actorOf(Broker.props());
        clock.tell(new Init(5000, broker), null);
    }

    private Route createRoute() {
        return route(
                pathPrefix("registry", () -> getRegistryRoutes())
        );
    }

    private Route getRegistryRoutes() {
        return route(
                post(() -> //Register endpoint to clock
                    entity(Jackson.unmarshaller(Registration.class), registration -> {
                        CompletionStage<Result> register = Patterns.ask(broker, registration, TIMEOUT)
                                .thenApply(Result.class::cast);

                        return onSuccess(() -> register, msg -> complete(StatusCodes.OK, msg, Jackson.marshaller()));
                    })
                ),
                delete(() -> { //Unregister current endpoint registered to clock
                            CompletionStage<Result> unregister = Patterns.ask(broker, new Unregister(), TIMEOUT)
                                    .thenApply(Result.class::cast);
                            return onSuccess(() -> unregister, msg -> complete(StatusCodes.OK, msg, Jackson.marshaller()));
                        }
                )
        );
    }
}