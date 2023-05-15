package main.java.com.javabootcamptestadvance.utils;

import java.util.Random;

public class IsbnGenerator {
    private static final String DIGITS = "0123456789";
    private static final int ISBN_LENGTH = 13;
    private static final Random RANDOM = new Random();

    public static String generateIsbn() {
        StringBuilder isbn = new StringBuilder(ISBN_LENGTH);

        for (int i = 0; i < ISBN_LENGTH - 1; i++) {
            char randomDigit = DIGITS.charAt(RANDOM.nextInt(DIGITS.length()));
            isbn.append(randomDigit);
        }

        int checkDigit = calculateIsbn13CheckDigit(isbn.toString());
        isbn.append(checkDigit);

        return isbn.toString();
    }

    private static int calculateIsbn13CheckDigit(String isbn) {
        int sum = 0;

        for (int i = 0; i < isbn.length(); i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            int weight = (i % 2 == 0) ? 1 : 3;
            sum += digit * weight;
        }

        int remainder = sum % 10;
        return (10 - remainder) % 10;
    }
}
