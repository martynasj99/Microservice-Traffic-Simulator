package simulator.model.action;

public class DecelerateFactory implements ActionFactory<DecelerateExecutor> {
    @Override
    public DecelerateExecutor create() {
        return new DecelerateExecutor();
    }
}
