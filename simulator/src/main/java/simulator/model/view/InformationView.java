package simulator.model.view;

import simulator.model.vehicle.RoundTrip;

import java.util.ArrayList;
import java.util.List;

public class InformationView {

    private List<String> crashLog = new ArrayList<>();
    private List<RoundTrip> roundTrips = new ArrayList<>();

    public InformationView() {
    }

    public void addCrashLog(String message){
        crashLog.add(message);
    }

    public void addTrip(RoundTrip trip){
        roundTrips.add(trip);
    }

    public List<String> getCrashLog() {
        return crashLog;
    }

    public void setCrashLog(List<String> crashLog) {
        this.crashLog = crashLog;
    }

    public List<RoundTrip> getRoundTrips() {
        return roundTrips;
    }

    public void setRoundTrips(List<RoundTrip> roundTrips) {
        this.roundTrips = roundTrips;
    }
}
