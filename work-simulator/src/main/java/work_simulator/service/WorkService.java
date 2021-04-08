package work_simulator.service;

import org.springframework.stereotype.Service;
import work_simulator.model.Work;

import java.util.HashMap;
import java.util.Map;

@Service
public class WorkService {

    private Map<Long, Work> workPlaces = new HashMap<>();

    public void addWork(Work work){
        workPlaces.put(work.getId(), work);
    }

    public Map<Long, Work> getWorkPlaces(){
        return workPlaces;
    }

    public Work getWork(Long id){
        return getWorkPlaces().get(id);
    }



}

