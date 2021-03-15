package simulator.model.action;

public class EnterIntersectionFactory implements ActionFactory<EnterIntersectionExecutor> {
    @Override
    public EnterIntersectionExecutor create() {
        return new EnterIntersectionExecutor();
    }
}
