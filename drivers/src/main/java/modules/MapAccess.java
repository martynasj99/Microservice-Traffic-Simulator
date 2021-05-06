package modules;

import astra.core.Module;
import astra.term.ListTerm;
import model.DayPlan;

import java.lang.reflect.Field;
import java.time.DayOfWeek;
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
    @TERM
    public Object getObjectByKey(Object object, String fieldName, String key){
        try {
            Field field = object.getClass().getField(fieldName);
            HashMap<String, ?> hashMap = (HashMap<String, ?>) field.get(object);
            return hashMap.get(key);
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }
    @TERM
    public String getStringByKey(Object object, String fieldName, String key){
        try {
            //Object object = listTerm.get(0);
            Field field = object.getClass().getField(fieldName);
            HashMap<String, String> hashMap = (HashMap<String, String>) field.get(object);
            return hashMap.containsKey(key) ? hashMap.get(key) : "";
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }
    @TERM
    public boolean containsKey(Object object, String fieldName, String key){
        try {
            Field field = object.getClass().getField(fieldName);
            HashMap<String, String> hashMap = (HashMap<String, String>) field.get(object);
            return hashMap.size() > 0 && hashMap.containsKey(key);
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

}
