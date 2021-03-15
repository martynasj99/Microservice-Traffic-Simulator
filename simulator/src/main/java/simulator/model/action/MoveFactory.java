package simulator.model.action;

public class MoveFactory implements ActionFactory<MoveExecutor> {
    @Override
    public MoveExecutor create() {
        return new MoveExecutor();
    }
}
