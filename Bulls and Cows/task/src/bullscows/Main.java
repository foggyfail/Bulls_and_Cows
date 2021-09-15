package bullscows;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static int bulls;
    static int cows;
    static int lengthOfNumber;
    static int possibleSymbols;

    public static void main(String[] args) {
        int turn = 0;

        System.out.println("Please, enter the secret code's length:");
        try {
            lengthOfNumber = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Error");
            System.exit(1);
        }

        String[] secretCode = new String[lengthOfNumber];

        System.out.println("Input the number of possible symbols in the code:");
        possibleSymbols = scanner.nextInt();

        if (lengthOfNumber > possibleSymbols) {
            System.out.println("Error: it's not possible to generate a code with a length of " + lengthOfNumber + " with " + possibleSymbols + "  unique symbols.");
            System.exit(1);
        }

        char app = (char) (possibleSymbols + 86);

        if (lengthOfNumber > 36 || lengthOfNumber < 1 || possibleSymbols > 36) {
            System.out.println("Error. Wrong length");
        } else {
            StringBuilder sb = new StringBuilder("The secret is prepared: ");
            sb.append("*".repeat(lengthOfNumber));
            if (possibleSymbols > 10) {
                sb.append(" (0-9, a-").append(app).append(")");
            } else {
                sb.append(" (0-").append(possibleSymbols - 1).append(")");
            }
            System.out.println(sb);
            System.out.println("Okay, let's start a game!");
            String[] secretCode1 = generateRandomNumbers(lengthOfNumber, possibleSymbols);
            String[] secretCode2;
            if (possibleSymbols > 10) {
                secretCode2 = generateRandomLetters(lengthOfNumber, possibleSymbols);
                for (int i = 0; i < lengthOfNumber; i++) {
                    int chooseSymbol = random.nextInt(2);
                    if (chooseSymbol == 0) {
                        secretCode[i] = secretCode1[i];
                    } else {
                        if (possibleSymbols > 10) {
                            secretCode[i] = secretCode2[i];
                        } else {
                            i--;
                        }
                    }
                }
            } else {
                System.arraycopy(secretCode1, 0, secretCode, 0, lengthOfNumber);
            }
            System.out.println(Arrays.toString(secretCode));
            do {
                bulls = 0;
                cows = 0;
                System.out.println("Turn " + ++turn + ":");
                String[] inputString = scanner.next().split("");
                if (inputString.length > lengthOfNumber) {
                    System.out.println("Error: " + Arrays.toString(inputString) + "isn't a valid number.");
                    System.exit(1);
                }
                containBullsAndCows(inputString, secretCode);
                System.out.println("Grade: " + bulls + " bull and " + cows + " cow");
            } while (bulls != lengthOfNumber);
            System.out.println("Congratulations! You guessed the secret code.");
        }
    }

    static void containBullsAndCows(String[] secretCode, String[] inputStrings) {
        for (int i = 0; i < secretCode.length; i++) {
            for (int j = 0; j < inputStrings.length; j++) {
                if (secretCode[i].equals(inputStrings[j])) {
                    cows++;
                    if (i == j) {
                        cows--;
                        bulls++;
                    }
                }
            }
        }
    }

    static String[] generateRandomNumbers(int lengthOfNumber, int possibleSymbols) {

        int[] secretCode = new int[lengthOfNumber];
        int len = 0;
        int number = 0;
        boolean counter = true;

        len = Math.min(possibleSymbols, 10);

        secretCode[0] = random.nextInt(len);

        for (int i = 1; i < lengthOfNumber; i++) {
            number = random.nextInt(len);
            for (int j = 0; j < i; j++) {
                if (number != secretCode[j]) {
                    counter = true;
                } else {
                    counter = false;
                    break;
                }
            }
            if (counter) {
                secretCode[i] = number;
            } else {
                i--;
            }
        }

        String[] code = new String[lengthOfNumber];

        for (int i = 0; i < lengthOfNumber; i++) {
            code[i] = Integer.toString(secretCode[i]);
        }

        return code;
    }

    static String[] generateRandomLetters(int lengthOfNumber, int possibleSymbols) {

        char[] secretCode = new char[lengthOfNumber];
        int number = 0;
        int len = possibleSymbols - 10;
        boolean counter = true;

        for (int i = 0; i < lengthOfNumber; i++) {
            number = random.nextInt(len) + 97;
            for (int j = 0; j < i; j++) {
                if (number != secretCode[j]) {
                    counter = true;
                } else {
                    counter = false;
                    break;
                }
            }
            if (counter) {
                secretCode[i] = (char) number;
            } else {
                i--;
            }
        }

        String[] code = new String[lengthOfNumber];

        for (int i = 0; i < lengthOfNumber; i++) {
            code[i] = String.valueOf(secretCode[i]);
        }

        return code;
    }
}
