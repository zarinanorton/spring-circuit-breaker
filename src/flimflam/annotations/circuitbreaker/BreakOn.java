package flimflam.annotations.circuitbreaker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface BreakOn {
    // timeout between tries in milliseconds.
    int timeout() default 10000;
    Class<? extends Throwable>[] value();
}
