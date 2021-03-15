package simulator;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {

    private static Logging logging = new Logging();

    private Logger logger = Logger.getLogger("MyLog");
    FileHandler fh;

    private Logging() {
        try {
            fh = new FileHandler("C:\\Users\\User\\IdeaProjects\\microservice-traffic-simulator\\LogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logging getInstance(){
        return logging;
    }

    public Logger getLogger() {
        return logger;
    }
}
