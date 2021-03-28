package home_simulator.model;

public class Home {

    private Long id;
    private Action nextAction;
    private String notificationUri;

    public Home() {
    }

    public void execute(){
        System.out.println("Watching TV!");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action getNextAction() {
        return nextAction;
    }

    public void setNextAction(Action nextAction) {
        this.nextAction = nextAction;
    }

    public String getNotificationUri() {
        return notificationUri;
    }

    public void setNotificationUri(String notificationUri) {
        this.notificationUri = notificationUri;
    }
}
