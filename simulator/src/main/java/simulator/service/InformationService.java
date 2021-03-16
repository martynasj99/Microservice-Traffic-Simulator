package simulator.service;

import org.springframework.stereotype.Service;
import simulator.model.view.InformationView;
import simulator.utils.GlobalClock;

@Service
public class InformationService {

    public final GlobalClock clock = GlobalClock.getInstance();

    private InformationView informationView = new InformationView();


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
