package modules;

import astra.core.Module;

public class LogicConversion extends Module {
    @TERM
    public boolean OR(boolean a, boolean b){
        return a || b;
    }
}
