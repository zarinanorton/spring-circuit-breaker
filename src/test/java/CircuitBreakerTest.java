import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.BrokenService;

public class CircuitBreakerTest {

    BrokenService brokenService = new BrokenService();
    @Test
    public void testBreakOn_vanilla_noConnection() {
        // expect no error to be thrown.
        brokenService.alwaysErrorsOut();
    }

    @Test
    public void testBreakOn_vanilla_runTimeException() {
        // expect no error to be thrown.
        Assertions.assertThrows(RuntimeException.class, () ->
                brokenService.alwaysErrorsOutWithGenericRuntimeException());
    }

}
