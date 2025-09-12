package org.example.view;

import org.example.messages.ErrorMessage;

import java.util.Scanner;

public class InputView {

    private static Scanner sc = new Scanner(System.in);

    public static String getUserStringInput() {
        return sc.next();
    }

    public static Integer getUserIntegerInput() {
        String input;
        while (true) {
            try {
                if (sc.hasNextInt()) {
                    return sc.nextInt();
                } else {
                    System.out.println("정수 형태로 입력해주세요.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.INVALID_INPUT.getMessage());
            }
        }
    }

    public static double getUserDoubleInput() {
        while (true) {
            try {
                if (sc.hasNextDouble()) {
                    return sc.nextDouble();
                } else {
                    System.out.println("소수 형태로 입력해주세요.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.INVALID_INPUT.getMessage());
            }
        }
    }
}
