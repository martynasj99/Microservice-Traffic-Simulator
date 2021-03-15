package simulator.model.action;

public class WaitFactory implements ActionFactory<WaitExecutor> {
    @Override
    public WaitExecutor create() {
        return new WaitExecutor();
    }
}
