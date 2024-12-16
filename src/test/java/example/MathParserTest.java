package org.example;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MathParserTest {


    @Test
    public void toRPNTest1() {
        Map<String, Double> variables = Map.of(
                "x", 1.0,
                "y", 1.0,
                "z", 1.0,
                "pi", Math.PI);


        String str = "2*3.14*(1-cos(3.4*x*(y/z)))/(1+sqrt(x+y+z)/2)";
        String rpnExpression = MathParser.toRPN(str, variables);
        assertEquals(rpnExpression, "2 3.14 * 1 3.4 x * y z / * cos - * 1 x y + z + sqrt 2 / + /");
    }

    @Test
    public void toRPNTest2() {
        Map<String, Double> variables = Map.of(
                "x", 7.1,
                "y", 0.6,
                "z", 5.0,
                "pi", Math.PI);


        String str = "(x+y)*(z/2)";
        String rpnExpression = MathParser.toRPN(str, variables);
        assertEquals(rpnExpression, "x y + z 2 / *");
    }

    @Test
    public void toRPNTest3() {
        Map<String, Double> variables = Map.of(
                "x", 9.0,
                "y", 1.0,
                "z", 1.0,
                "pi", Math.PI);


        String str = "sqrt(x)+sqr(y)-ln(z)";
        String rpnExpression = MathParser.toRPN(str, variables);
        assertEquals(rpnExpression, "x sqrt y sqr + z ln -");
    }

    @Test
    public void toRPNTest4() {
        Map<String, Double> variables = Map.of(
                "x", 1.0,
                "y", 1.0,
                "z", 1.0,
                "pi", Math.PI);


        String str = "round(cos(pi))+(tan(pi)/sqrt(9))";
        String rpnExpression = MathParser.toRPN(str, variables);
        assertEquals(rpnExpression, "pi cos round pi tan 9 sqrt / +");
    }


    @Test
    public void evaluateTest1() {
        Map<String, Double> variables = Map.of(
                "x", 1.0,
                "y", 1.0,
                "z", 1.0,
                "pi", Math.PI);


        String str = "2*3.14*(1-cos(3.4*x*(y/z)))/(1+sqrt(x+y+z)/2)";
        String rpnExpression = MathParser.toRPN(str, variables);
        double result = MathParser.evaluate(rpnExpression, variables);
        assertEquals(result, 6.619, 0.001);
    }

    @Test
    public void evaluateTest2() {
        Map<String, Double> variables = Map.of(
                "x", 7.1,
                "y", 0.6,
                "z", 5.0,
                "pi", Math.PI);


        String str = "(x+y)*(z/2)";
        String rpnExpression = MathParser.toRPN(str, variables);
        double result = MathParser.evaluate(rpnExpression, variables);
        assertEquals(result, 19.25, 0.001);
    }

    @Test
    public void evaluateTest3() {
        Map<String, Double> variables = Map.of(
                "x", 9.0,
                "y", 1.0,
                "z", 1.0,
                "pi", Math.PI);


        String str = "sqrt(x)+sqr(y)-ln(z)";
        String rpnExpression = MathParser.toRPN(str, variables);
        double result = MathParser.evaluate(rpnExpression, variables);
        assertEquals(result, 4.0, 0.000001);
    }

    @Test
    public void evaluateTest4() {
        Map<String, Double> variables = Map.of(
                "x", 1.0,
                "y", 1.0,
                "z", 1.0,
                "pi", Math.PI);


        String str = "round(cos(pi))+(tan(pi)/sqrt(9))";
        String rpnExpression = MathParser.toRPN(str, variables);
        double result = MathParser.evaluate(rpnExpression, variables);
        assertEquals(result, -1.0, 0.00001);
    }

}