package pojo;

import annotations.circuitbreaker.BreakOn;
import annotations.circuitbreaker.ProtectedCircuit;

@ProtectedCircuit
public class BrokenService {

    @BreakOn(value = NoConnectionException.class)
    public void alwaysErrorsOut() {
        throw new NoConnectionException("Always errors out!");
    }

    @BreakOn(value = NoConnectionException.class)
    public void alwaysErrorsOutWithGenericRuntimeException() {
        throw new RuntimeException("Always errors out!");
    }
}
