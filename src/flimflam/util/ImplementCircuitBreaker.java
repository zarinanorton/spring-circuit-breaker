package util;

import annotations.circuitbreaker.BreakOn;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class ImplementCircuitBreaker {

    public Map<String, CallLog> callLogs = new HashMap<>();

    private CallLog supply(String className) {
        CallLog found = callLogs.getOrDefault(className, new CallLog(className));
        return found;
    }

    // wrap functions in a try/catch, and if an exception is thrown, set a timeout before trying again...
    @Around("@annotation(breakOn)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint, BreakOn breakOn) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        int timeout = breakOn.timeout();
        // if it is an instance, then start the timeout
        var parentClass = joinPoint.getSourceLocation().getWithinType();
        var currentTime = System.currentTimeMillis();
        CallLog callLog = supply(parentClass.getCanonicalName());
        Long lastCalledAt = callLog.getLastTimeCalled(methodName);
        try {
            if (lastCalledAt == -1 || lastCalledAt + timeout >= currentTime) {
                return joinPoint.proceed(joinPoint.getArgs());
            } else {
                return null;
            }
        } catch (Throwable ex) {
            var throwablesToBreakOn = breakOn.value();
            if (throwablesToBreakOn == null || throwablesToBreakOn.length == 0) {
                throw ex;
            } else {
                for (int i = 0; i < throwablesToBreakOn.length; i++) {
                    if (throwablesToBreakOn[i].isInstance(ex)) {
                        // increment the last time we called this method
                        return null;
                    }
                }
                throw ex;
            }
        }
    }
}
