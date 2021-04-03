package simulator.service;

import org.springframework.stereotype.Service;
import simulator.model.view.InformationView;

import java.util.List;

@Service
public class InformationService {

    private InformationView informationView = new InformationView();

    public void addCrash(String message){
        informationView.addCrashLog(message);
    }

    public List<String> getCrashLog(){
        return informationView.getCrashLog();
    }

    public InformationView getInformationView() {
        return informationView;
    }
}
