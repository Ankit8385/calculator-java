package org.example;
import java.util.*;

public class Calculator {
    public static double evaluateExpression(String expression) {
        List<String> postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    // Function to change infix to postfix
    private static List<String> infixToPostfix(String expression) {
        Stack<Character> operators = new Stack<>();
        List<String> output = new ArrayList<>();

        // Setting the precedence
        Map<Character, Integer> precedence = Map.of(
                '+', 1, '-', 1,
                '*', 2, '/', 2
        );

        StringBuilder numBuffer = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                numBuffer.append(c);
            } else {
                if (numBuffer.length() > 0) {
                    output.add(numBuffer.toString());
                    numBuffer.setLength(0);
                }

                if (c == '(') {
                    operators.push(c);
                } else if (c == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        output.add(String.valueOf(operators.pop()));
                    }
                    // Remove '('
                    operators.pop();
                } else if (precedence.containsKey(c)) {
                    while (!operators.isEmpty() && precedence.containsKey(operators.peek()) &&
                            ((precedence.get(c) <= precedence.get(operators.peek())) ||
                                    (precedence.get(c) < precedence.get(operators.peek())))) {
                        output.add(String.valueOf(operators.pop()));
                    }
                    operators.push(c);
                }
            }
        }

        if (numBuffer.length() > 0) {
            output.add(numBuffer.toString());
        }

        while (!operators.isEmpty()) {
            output.add(String.valueOf(operators.pop()));
        }

        return output;
    }

    private static double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (token.matches("-?\\d+(\\.\\d+)?")) { // Check if it's a number
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+" -> stack.push(a + b);
                    case "-" -> stack.push(a - b);
                    case "*" -> stack.push(a * b);
                    case "/" -> stack.push(a / b);
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an expression: ");
        String expression = scanner.nextLine().replaceAll("\\s+", ""); // Remove spaces

        try {
            double result = evaluateExpression(expression);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Invalid Expression!");
        }
    }
}
