package org.example;

import java.util.List;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the expression: ");
        String str = sc.nextLine().replaceAll("\\s+" , "");

        try {
            double res = evaluteExpression(str);
            System.out.println("Output: " + res);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}