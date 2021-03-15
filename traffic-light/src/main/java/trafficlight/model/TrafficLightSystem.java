package trafficlight.model;

import trafficlight.exception.UnsupportedNumberOfLinksException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrafficLightSystem {

    private List<Phase> phases;
    private List<Long> inLinks;
    private List<Long> outLinks;
    private int state;

    public TrafficLightSystem(List<Long> inLinks, List<Long> outLinks) {
        this.inLinks = inLinks;
        this.outLinks = outLinks;
        this.state = 0;
        generatePhases();
    }

    public void generatePhases(){
        phases = new ArrayList<>();
        int in = inLinks.size();
        int out = outLinks.size();
        if(in == 1 && out == 1) {
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.GREEN)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.AMBER)));
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.RED)));
        }
        if(in == 2 && out == 2){
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.GREEN, TrafficLightSignals.GREEN, TrafficLightSignals.RED, TrafficLightSignals.RED)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.AMBER, TrafficLightSignals.AMBER, TrafficLightSignals.RED, TrafficLightSignals.RED)));
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.GREEN, TrafficLightSignals.GREEN)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.AMBER, TrafficLightSignals.AMBER)));
        }
        else if(in == 2 && out == 1){
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.GREEN, TrafficLightSignals.RED)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.AMBER, TrafficLightSignals.RED)));
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.GREEN)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.AMBER)));
        }else if(in == 1 && out == 2){
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.GREEN, TrafficLightSignals.GREEN)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.AMBER, TrafficLightSignals.AMBER)));
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.RED)));
        }else if(in == 1 && out == 3){
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.GREEN, TrafficLightSignals.GREEN, TrafficLightSignals.GREEN)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.AMBER, TrafficLightSignals.AMBER, TrafficLightSignals.AMBER)));
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.RED)));
        }else if(in == 3 && out == 1){
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.GREEN, TrafficLightSignals.RED, TrafficLightSignals.RED)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.AMBER, TrafficLightSignals.RED, TrafficLightSignals.RED)));
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.GREEN)));
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.AMBER)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.GREEN, TrafficLightSignals.RED)));
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.AMBER, TrafficLightSignals.RED)));
        }else if(in == 3 && out == 2){
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.GREEN, TrafficLightSignals.GREEN, TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.RED)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.AMBER, TrafficLightSignals.AMBER, TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.RED)));
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.GREEN, TrafficLightSignals.GREEN)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.AMBER, TrafficLightSignals.AMBER)));
            phases.add(new Phase(5, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.GREEN, TrafficLightSignals.GREEN, TrafficLightSignals.RED, TrafficLightSignals.RED)));
            phases.add(new Phase(1, Arrays.asList(TrafficLightSignals.RED, TrafficLightSignals.RED, TrafficLightSignals.AMBER, TrafficLightSignals.AMBER, TrafficLightSignals.RED, TrafficLightSignals.RED)));
        }
        else{
            throw new UnsupportedNumberOfLinksException("The number of in links and out links of this node is not yet supported.");
        }
    }

    public Phase getTLSState(){
        return phases.get(state);
    }

    public void nextState(){
        state = (state + 1) % phases.size();
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }

    public List<Long> getInLinks() {
        return inLinks;
    }

    public void setInLinks(List<Long> inLinks) {
        this.inLinks = inLinks;
    }

    public List<Long> getOutLinks() {
        return outLinks;
    }

    public void setOutLinks(List<Long> outLinks) {
        this.outLinks = outLinks;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
