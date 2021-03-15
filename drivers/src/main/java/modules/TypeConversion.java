package modules;

import astra.core.Module;

public class TypeConversion extends Module {
    @TERM
    public int stringToInt(String s){
        return Integer.parseInt(s);
    }
}
