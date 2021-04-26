package modules;

import astra.core.Module;

import java.lang.reflect.Field;
import java.util.HashMap;

public class MapAccess extends Module {
    @TERM
    public Object getObjectByKey(Object object, String fieldName, Integer key){
        try {
            Field field = object.getClass().getField(fieldName);
            HashMap<Long, ?> hashMap = (HashMap<Long, ?>) field.get(object);
            return hashMap.get(key.longValue());
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

}
