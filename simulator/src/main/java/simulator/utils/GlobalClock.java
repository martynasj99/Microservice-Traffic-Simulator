package simulator.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GlobalClock {

    private final int STEP_MULTIPLIER = 5;

    private final static GlobalClock clock = new GlobalClock();
    private Date time;

    public static GlobalClock getInstance(){
        return clock;
    }

    private GlobalClock() {
        time = new Date();
    }

    public void setTime(int step){
        Calendar cal = Calendar.getInstance();
        int min = step*STEP_MULTIPLIER;
        cal.set(Calendar.HOUR_OF_DAY, (min/60)%24);
        cal.set(Calendar.MINUTE, min%60);
        time = cal.getTime();
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(time);
    }
}
