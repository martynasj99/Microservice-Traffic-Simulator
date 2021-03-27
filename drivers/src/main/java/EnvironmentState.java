import java.util.Set;

public class EnvironmentState {

    public int id;
    public String time;

    public int vehicleSpeed;
    public int streetSpeed;
    public boolean trafficLightStatus;

    public int intersectionCurrentCapacity;
    public int intersectionMaxCapacity;

    public boolean atIntersection;
    public boolean atLastCell;
    public boolean hasArrived;
    public boolean hasEndNode;

    public boolean vehicleAhead;
    public boolean obstacleInVision;
    public boolean obstacleAhead;

    public boolean canLeave;

    public Set<String> possibleActions;
}
