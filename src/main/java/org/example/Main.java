package org.example;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        System.out.print("Enter password length: ");
        Scanner scanner = new Scanner(System.in);
        int passwordLength = scanner.nextInt();
        scanner.close();

        String password = PasswordGenerator.generateNewPassword(passwordLength);
        System.out.println(password);
    }
}