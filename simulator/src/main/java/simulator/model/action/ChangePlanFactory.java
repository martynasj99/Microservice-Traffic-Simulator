package simulator.model.action;

public class ChangePlanFactory implements ActionFactory<ChangePlanExecutor> {
    @Override
    public ChangePlanExecutor create() {
        return new ChangePlanExecutor();
    }
}
