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
        return callLogs.getOrDefault(className, new CallLog(className));
    }
    // wrap functions in a try/catch, and if an exception is thrown, set a timeout before trying again...
    @Around("@annotation(breakOn)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint, BreakOn breakOn) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        // if it is an instance, then start the timeout
        Class parentClass = joinPoint.getSourceLocation().getWithinType();
        int timeout = breakOn.timeout();
        long currentTime = System.currentTimeMillis();

        CallLog callLog = supply(parentClass.getCanonicalName());
        Long lastCalledAt = callLog.getLastTimeCalled(methodName);
        try {
            if (lastCalledAt == -1 || lastCalledAt + timeout >= currentTime) {
                return joinPoint.proceed(joinPoint.getArgs());
            } else {
                return null;
            }
        } catch (Throwable ex) {
            Class[] throwables = breakOn.value() == null ? new Class[]{} : breakOn.value();
            for (int i = 0; i < throwables.length; i++) {
                if (throwables[i].isInstance(ex)) {
                    // increment the last time we called this method
                    callLog.updateLastTimeCalled(methodName, currentTime);
                    return null;
                }
            }
            throw ex;
        }
    }
}
