package org.example;

import java.util.*;

public class MathParser {

    private static final Set<String> FUNCTIONS = Set.of(
            "exp", "ln", "sqr", "sqrt", "cos", "sin", "tan", "acos", "asin", "atan", "round"
    );

    public static double evaluate(String expression, Map<String, Double> variables) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = expression.split(" ");

        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (variables.containsKey(token)) {
                stack.push(variables.get(token));
            } else if (FUNCTIONS.contains(token)) {
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Invalid expression: missing operand for function " + token);
                }
                double value = stack.pop();
                stack.push(applyFunction(token, value));
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression: missing operands for operator " + token);
                }
                double b = stack.pop();
                double a = stack.pop();
                stack.push(applyOperator(token, a, b));
            } else {
                throw new IllegalArgumentException("Unknown token: " + token);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression: too many operands");
        }

        return stack.pop();
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String token) {
        return "+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token);
    }

    private static double applyOperator(String operator, double a, double b) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) throw new ArithmeticException("Division by zero");
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }



//    return switch (function) {
//        case "exp" -> Math.exp(value);
//        case "ln" -> {
//            if (value <= 0) throw new ArithmeticException("Logarithm of non-positive number");
//            yield Math.log(value);
//        }
//        case "sqr" -> value * value;
//        case "sqrt" -> {
//            if (value < 0) throw new ArithmeticException("Square root of negative number");
//            yield Math.sqrt(value);
//        }
//        case "cos" -> Math.cos(value);
//        case "sin" -> Math.sin(value);
//        case "tan" -> Math.tan(value);
//        case "acos" -> {
//            if (value < -1 || value > 1) throw new ArithmeticException("acos out of range");
//            yield Math.acos(value);
//        }
//        case "asin" -> {
//            if (value < -1 || value > 1) throw new ArithmeticException("asin out of range");
//            yield Math.asin(value);
//        }
//        case "atan" -> Math.atan(value);
//        case "round" -> (double) Math.round(value);
//        default -> throw new IllegalArgumentException("Unknown function: " + function);
//    };
//}

    private static double applyFunction(String function, double value) {
        return switch (function) {
            case "exp" -> java.lang.Math.exp(value);
            case "ln" -> java.lang.Math.log(value);
            case "sqr" -> value * value;
            case "sqrt" -> java.lang.Math.sqrt(value);
            case "cos" -> java.lang.Math.cos(value);
            case "sin" -> java.lang.Math.sin(value);
            case "tan" -> java.lang.Math.tan(value);
            case "acos" -> java.lang.Math.acos(value);
            case "asin" -> java.lang.Math.asin(value);
            case "atan" -> java.lang.Math.atan(value);
            case "round" -> (double) java.lang.Math.round(value);
            default -> throw new IllegalArgumentException("Unknown function: " + function);
        };
    }

    public static String toRPN(String infix, Map<String, Double> variables) {
        StringBuilder output = new StringBuilder();
        Stack<String> operators = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, "()+-*/", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) continue;

            if (isNumeric(token) || variables.containsKey(token)) {
                output.append(token).append(" ");
            } else if (FUNCTIONS.contains(token)) {
                operators.push(token);
            } else if (isOperator(token)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    output.append(operators.pop()).append(" ");
                }
                operators.push(token);
            } else if ("(".equals(token)) {
                operators.push(token);
            } else if (")".equals(token)) {
                while (!operators.isEmpty() && !"(".equals(operators.peek())) {
                    output.append(operators.pop()).append(" ");
                }
                if (operators.isEmpty() || !"(".equals(operators.pop())) {
                    throw new IllegalArgumentException("Mismatched parentheses in expression");
                }
                if (!operators.isEmpty() && FUNCTIONS.contains(operators.peek())) {
                    output.append(operators.pop()).append(" ");
                }
            } else {
                throw new IllegalArgumentException("Unknown token: " + token);
            }
        }

        while (!operators.isEmpty()) {
            String op = operators.pop();
            if ("(".equals(op) || ")".equals(op)) {
                throw new IllegalArgumentException("Mismatched parentheses in expression");
            }
            output.append(op).append(" ");
        }

        return output.toString().trim();
    }

    private static int precedence(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }

}

