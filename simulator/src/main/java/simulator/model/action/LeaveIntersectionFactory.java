package simulator.model.action;

public class LeaveIntersectionFactory implements ActionFactory<LeaveIntersectionExecutor> {
    @Override
    public LeaveIntersectionExecutor create() {
        return new LeaveIntersectionExecutor();
    }
}