package clock;

import akka.actor.AbstractActor;
import akka.actor.Props;
import clock.messages.Registration;
import clock.messages.Result;
import clock.messages.Step;
import clock.messages.Unregister;
import clock.util.RestClient;
import clock.util.WebResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedList;
import java.util.List;

public class Broker extends AbstractActor {
    public static Props props() { return  Props.create(Broker.class);}

    private List<Registration> registrations = new LinkedList<>();
    private RestClient restClient = new RestClient();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Registration.class, msg -> {
                    registrations.add(msg);
                    getSender().tell(new Result(), getSelf());
                })
                .match(Unregister.class, msg -> {
                    for(Registration registration : registrations){
                        if(registration.getName().equals(msg.getName())){
                            registrations.remove(registration);
                        }
                    }
                    getSender().tell(new Result(), getSelf());
                })
                .match(Step.class, msg -> {
                    String json = mapper.writeValueAsString(msg);
                    System.out.println("step [broker]: " + msg.getStep());
                    for(Registration registration : registrations){
                        System.out.println("Sending to: " + registration.getUri() + " ["+registration.getName()+ "]");
                        if(!registration.isFailed()){
                            WebResponse response = restClient.put(registration.getUri(), RestClient.JSON_TYPE, json);
                            if (response == null) registration.setFailed(true);
                        }
                    }
                })
                .build();
    }
}
