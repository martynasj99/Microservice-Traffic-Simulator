package simulator.model.action;

import simulator.model.action.ActionExecutor;

public interface ActionFactory<T extends ActionExecutor> {
    T create();
}
