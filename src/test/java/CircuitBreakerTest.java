import annotations.circuitbreaker.BreakOn;
import annotations.circuitbreaker.ProtectedCircuit;

@ProtectedCircuit
public class CircuitBreakerTest {

    @BreakOn(value = Exception.class)
    public void alwaysErrorsOut() {

    }
}
