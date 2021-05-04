package modules;

import astra.core.Module;
import astra.term.ListTerm;
import astra.term.Primitive;
import model.EnvironmentStreet;

import java.lang.reflect.Field;

public class RetrieveObject extends Module {

    @TERM
    public Object getObject(Object object, String fieldName) {
        try {
            Field field = object.getClass().getField(fieldName);
            return field.get(object);
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    @TERM
    public Object getObjectAtIndex(ListTerm list, int index){
        try {
            return ((Primitive) list.get(index)).value();
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
    
}
