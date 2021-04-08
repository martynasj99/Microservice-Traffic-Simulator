package work_simulator;

import work_simulator.model.Work;

import java.util.List;

public class Configuration {

    private List<Work> workPlaces;

    public List<Work> getWorkPlaces() {
        return workPlaces;
    }

    public void setWorkPlaces(List<Work> workPlaces) {
        this.workPlaces = workPlaces;
    }
}
