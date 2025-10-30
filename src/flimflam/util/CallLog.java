package util;

import java.util.HashMap;
import java.util.Map;

public class CallLog {
    final String className;
    final Map<String, Long> methodsToLastCall;
    public CallLog(String className) {
        this.className = className;
        this.methodsToLastCall = new HashMap<>();
    }
    public Long getLastTimeCalled(String methodName) {
        return methodsToLastCall.getOrDefault(methodName, -1l);
    }
    public void updateLastTimeCalled(String methodName, Long timestamp) {
        methodsToLastCall.put(methodName, timestamp);
    }
}
