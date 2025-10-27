package flimflam.util;

import flimflam.annotations.circuitbreaker.BreakOn;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class ImplementCircuitBreaker {

    public Map<String, CallLog> callLogs = new HashMap<>();

    // wrap functions in a try/catch, and if an exception is thrown, set a timeout before trying again...
    @Around("@annotation(breakOn)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint, BreakOn breakOn) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            var values = breakOn.value();
            if (values == null || values.length == 0) {
                throw ex;
            } else {
                for (int i = 0; i < values.length; i++) {
                    if (values[i].isInstance(ex)) {
                        // if it is an instance, then start the timeout
                        var parentClass = joinPoint.getSourceLocation().getWithinType();
                        if (callLogs.containsKey(parentClass.getCanonicalName())) {
                            // get and update, else create a new entry...
                            return null;
                        }
                    }
                }
                throw ex;
            }
        }
    }
}
