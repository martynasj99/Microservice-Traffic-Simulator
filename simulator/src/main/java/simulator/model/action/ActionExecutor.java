package simulator.model.action;

import simulator.model.Vehicle;
import simulator.service.ServiceContext;

import java.util.List;

public interface ActionExecutor {
    boolean execute(Vehicle vehicle, ServiceContext serviceContext, List<String> parameters);
}
