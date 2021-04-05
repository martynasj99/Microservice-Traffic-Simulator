package simulator.service;

import org.springframework.stereotype.Service;
import simulator.model.vehicle.RoundTrip;
import simulator.model.view.InformationView;

import java.util.ArrayList;
import java.util.List;

@Service
public class InformationService {

    private InformationView informationView = new InformationView();
    private List<RoundTrip> roundTrips = new ArrayList<>();

    public void addCrash(String message){
        informationView.addCrashLog(message);
    }

    public List<String> getCrashLog(){
        return informationView.getCrashLog();
    }

    public InformationView getInformationView() {
        return informationView;
    }

    public void addTrip(RoundTrip trip){
        roundTrips.add(trip);
    }

    public List<RoundTrip> getRoundTrips(){
        return roundTrips;
    }
}
