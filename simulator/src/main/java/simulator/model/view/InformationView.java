package simulator.model.view;

import java.util.List;

public class InformationView {

    private List<String> crashLog;

    public InformationView() {
    }

    public void addCrashLog(String message){
        crashLog.add(message);
    }

    public List<String> getCrashLog() {
        return crashLog;
    }

    public void setCrashLog(List<String> crashLog) {
        this.crashLog = crashLog;
    }
}
