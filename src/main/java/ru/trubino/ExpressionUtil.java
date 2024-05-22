package ru.trubino;

import java.util.*;

/**
 * Utility class for evaluating mathematical expressions containing variables.
 */
public class ExpressionUtil {

    /**
     * Evaluates a mathematical expression that may contain variables.
     *
     * @param expression the mathematical expression to evaluate
     * @return the result of the evaluated expression
     */
    public static double evaluate(String expression) {
        expression = expression.replaceAll("\\s+", "");

        if (!isValidExpression(expression)) {
            System.out.println("Error: Invalid expression!");
            return Double.NaN;
        }

        Set<String> variables = getVariables(expression);
        Scanner scanner = new Scanner(System.in);

        Map<String, Double> variableValues = new HashMap<>();
        for (String variable : variables) {
            System.out.print("Enter the value for variable " + variable + ": ");
            double value = scanner.nextDouble();
            variableValues.put(variable, value);
        }

        for (Map.Entry<String, Double> entry : variableValues.entrySet()) {
            expression = expression.replaceAll(entry.getKey(), Double.toString(entry.getValue()));
        }

        return calculate(expression);
    }

    /**
     * Checks if the given expression has balanced parentheses.
     *
     * @param expression the expression to check
     * @return true if the expression is valid, false otherwise
     */
    private static boolean isValidExpression(String expression) {
        int balance = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
            }
        }
        return balance == 0;
    }

    /**
     * Extracts variable names from the given expression.
     *
     * @param expression the expression to extract variables from
     * @return a set of variable names
     */
    private static Set<String> getVariables(String expression) {
        Set<String> variables = new HashSet<>();
        StringBuilder variableBuilder = new StringBuilder();
        for (char c : expression.toCharArray()) {
            if (Character.isLetter(c)) {
                variableBuilder.append(c);
            } else {
                if (variableBuilder.length() > 0) {
                    variables.add(variableBuilder.toString());
                    variableBuilder.setLength(0);
                }
            }
        }
        if (variableBuilder.length() > 0) {
            variables.add(variableBuilder.toString());
        }
        return variables;
    }

    /**
     * Calculates the result of the given mathematical expression.
     *
     * @param expression the expression to calculate
     * @return the result of the calculation
     */
    private static double calculate(String expression) {
        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (operators.peek() != '(') {
                    processOperator(operands, operators);
                }
                operators.pop();
            } else if (isOperator(ch)) {
                while (!operators.isEmpty() && hasHigherPrecedence(operators.peek(), ch)) {
                    processOperator(operands, operators);
                }
                operators.push(ch);
            } else {
                StringBuilder operandBuilder = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    operandBuilder.append(expression.charAt(i++));
                }
                i--;
                operands.push(Double.parseDouble(operandBuilder.toString()));
            }
        }

        while (!operators.isEmpty()) {
            processOperator(operands, operators);
        }

        return operands.pop();
    }

    /**
     * Processes the top operator in the stack with the top two operands.
     *
     * @param operands  the stack of operands
     * @param operators the stack of operators
     */
    private static void processOperator(Stack<Double> operands, Stack<Character> operators) {
        char op = operators.pop();
        double operand2 = operands.pop();
        double operand1 = operands.pop();
        double result = performOperation(operand1, operand2, op);
        operands.push(result);
    }

    /**
     * Checks if the given character is an operator.
     *
     * @param ch the character to check
     * @return true if the character is an operator, false otherwise
     */
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    /**
     * Checks if the first operator has higher precedence than the second.
     *
     * @param op1 the first operator
     * @param op2 the second operator
     * @return true if the first operator has higher precedence, false otherwise
     */
    private static boolean hasHigherPrecedence(char op1, char op2) {
        return (op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-');
    }

    /**
     * Performs the given operation on two operands.
     *
     * @param operand1 the first operand
     * @param operand2 the second operand
     * @param operator the operator
     * @return the result of the operation
     */
    private static double performOperation(double operand1, double operand2, char operator) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero!");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}