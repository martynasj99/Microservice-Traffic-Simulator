package simulator.model.action;

import simulator.service.ServiceContext;
import simulator.model.Vehicle;

import java.util.List;

public class WaitExecutor implements ActionExecutor {

    @Override
    public boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters) {
        return true;
    }
}
