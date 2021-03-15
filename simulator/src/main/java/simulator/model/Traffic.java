package simulator.model;

public class Traffic {

    private int cells;
    private Vehicle[] traffic;

    public Traffic(int cells) {
        this.cells = cells;
        this.traffic = new Vehicle[cells];
    }

    public Vehicle[] getTraffic() {
        return traffic;
    }

    public void setTraffic(Vehicle[] traffic) {
        this.traffic = traffic;
    }

    public int getCells() {
        return cells;
    }

    public void setCells(int cells) {
        this.cells = cells;
    }
}
