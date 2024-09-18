package org.example;

import java.security.SecureRandom;

public class PasswordGenerator {

    public String generateNewPassword(int passwordLength) {

        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(symbols.length());
            char randomSymbol = symbols.charAt(randomIndex);
            password.append(randomSymbol);
        }

        return password.toString();
    }

}
