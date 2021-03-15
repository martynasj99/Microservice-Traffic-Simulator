package trafficlight.service;

import org.springframework.stereotype.Service;
import trafficlight.model.Phase;
import trafficlight.model.State;
import trafficlight.model.TrafficLightSignals;
import trafficlight.model.TrafficLightSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrafficLightService {

    private Map<Long, TrafficLightSystem> trafficLights = new HashMap<>(); //NODE ID -> TRAFFIC LIGHT
    private Map<Long, Boolean> canGoAtStreet; //STREET ID -> TRUE/FALSE

    public Map<Long, TrafficLightSystem> getTrafficLights(){
        return trafficLights;
    }

    public void addTrafficLight(Long nodeId, List<Long> inLinks, List<Long> outLinks){
        TrafficLightSystem trafficLightSystem = new TrafficLightSystem(inLinks, outLinks);
        trafficLights.put(nodeId, trafficLightSystem);
    }

    private void computeTrafficLightStatus(){
        Map<Long, Boolean> canGoAtStreet = new HashMap<>();
        for(TrafficLightSystem trafficLightSystem : trafficLights.values()){
            Phase curPhase = trafficLightSystem.getTLSState();
            List<Long> tlsInLinks = trafficLightSystem.getInLinks();
            List<Long> tlsOutLinks = trafficLightSystem.getOutLinks();
            for(Long fromStreetId : tlsInLinks){
                int i = 0;
                while(i < tlsInLinks.size() && !tlsInLinks.get(i).equals(fromStreetId)) i++;
                TrafficLightSignals signals = curPhase.getState().get( i * tlsOutLinks.size());
                canGoAtStreet.put(fromStreetId, signals == TrafficLightSignals.GREEN);
            }
        }
        this.canGoAtStreet = canGoAtStreet;
    }

    public Map<Long, Boolean> getStreetStatuses(){
        return this.canGoAtStreet;
    }

    public void step(State state){
        for(Map.Entry<Long, TrafficLightSystem> entry : trafficLights.entrySet()){
            TrafficLightSystem trafficLightSystem = entry.getValue();
            if(state.getStep() % trafficLightSystem.getTLSState().getDuration() == 0){
                trafficLightSystem.nextState();
            }
        }
        computeTrafficLightStatus();
    }
}
