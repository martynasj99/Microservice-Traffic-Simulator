package simulator.model.action;

public class AccelerateFactory implements ActionFactory<AccelerateExecutor> {

    @Override
    public AccelerateExecutor create() {
        return new AccelerateExecutor();
    }
}
