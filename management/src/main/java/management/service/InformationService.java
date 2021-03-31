package management.service;

import management.GlobalClock;
import management.model.InformationView;
import org.springframework.stereotype.Service;

@Service
public class InformationService {

    private InformationView informationView = new InformationView();
    private GlobalClock clock = GlobalClock.getInstance();

    public void setTime(int step){
        clock.setTime(step);
        this.informationView.setTime(clock.toString());
    }

    public String getTime(){
        return informationView.getTime();
    }

    public InformationView getInformationView(){
        return this.informationView;
    }

}
