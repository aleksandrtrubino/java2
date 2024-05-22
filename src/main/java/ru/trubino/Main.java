package ru.trubino;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the expression: ");
        String expression = scanner.nextLine();
        double result = ExpressionUtil.evaluate(expression);
        System.out.println("Result: " + result);
    }
}
