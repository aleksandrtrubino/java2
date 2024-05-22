import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.trubino.ExpressionUtil.evaluate;

public class ExpressionUtilTest {

    @Test
    public void testEvaluateSimpleArithmeticExpression() {
        assertEquals(6.0, evaluate("2+2*2"), 0.0001);
    }

    @Test
    public void testEvaluateExpressionWithVariables() {
        System.setIn(new ByteArrayInputStream("2".getBytes()));
        assertEquals(8.0, evaluate("2+x*3"), 0.0001);
        System.setIn(System.in);
    }

    @Test
    public void testEvaluateExpressionWithVariablesAndBrackets() {
        System.setIn(new ByteArrayInputStream("2".getBytes()));
        assertEquals(16.0, evaluate("(2+x)*4"), 0.0001);
        System.setIn(System.in);
    }

    @Test()
    public void testEvaluateDivisionByZero() {
        try {
            evaluate("2/0");
        } catch (ArithmeticException e) {
            assertEquals("Division by zero!", e.getMessage());
        }
    }

    @Test
    public void testEvaluateInvalidExpression() {
        assertEquals(Double.NaN, evaluate("(2+x*3"), 0.0001);
    }
}