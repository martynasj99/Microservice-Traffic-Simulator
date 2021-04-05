package simulator.model.action;

public interface ActionFactory<T extends ActionExecutor> {
    T create();
}
