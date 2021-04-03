package simulator.model.vehicle;

public class VehicleProgress {

    private int totalProgress;
    private int currentStreetProgress;

    public VehicleProgress() {
        this.totalProgress = 2;
    }

    public void nextTotalProgress(){
        this.totalProgress++;
    }

    public void nextCurrentStreetProgress(){
        this.currentStreetProgress++;
    }

    public int getTotalProgress() {
        return totalProgress;
    }

    public void setTotalProgress(int totalProgress) {
        this.totalProgress = totalProgress;
    }

    public int getCurrentStreetProgress() {
        return currentStreetProgress;
    }

    public void setCurrentStreetProgress(int currentStreetProgress) {
        this.currentStreetProgress = currentStreetProgress;
    }
}
