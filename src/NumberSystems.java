import java.math.BigInteger;
import java.util.Scanner;

public class NumberSystems {

    public int readOperationInput() {
        Scanner scanner = new Scanner(System.in);
        int digit = 0;
        while (scanner.hasNextLine()) {
            String digitString = scanner.nextLine();
            try {
                digit = Integer.parseInt(digitString);
                break;
            } catch (Exception e) {
                System.out.println("Sorry. Try again.");
            }
        }
        return digit;
    }

    public int firstSystemInput() {
        System.out.println("Of which number system do translate from? Enter the digit from 2 to 16");
        int digit = checkInput();
        return digit;
    }

    public int checkInput() {
        while (true) {
            int digit = readOperationInput();
            if (2 <= digit && digit <= 16) {
                return digit;
            } else {
                System.out.println("Sorry. Try again.");
            }
        }
    }

    public int secondSystemInput() {
        System.out.println("In which number system do translate from? Enter the digit from 2 to 16");
        int digit = checkInput();
        return digit;
    }

    private static int[][] createsArrayLetterEqualDigit() {
        /* Вообще можно было взять Character.getNumericValue(nameString.charAt()) и не маяться всей этой порнографией)))
        Но мы же не ищем легких путей, вот и будем трахаться дальше не зная про него))
        */
        int[][] array = {{97, 10}, {98, 11}, {99, 12}, {100, 13}, {101, 14}, {102, 15}};
        return array;
    }

    public String numberInput(int firstSystem) {
        System.out.println("Enter the number: ");
        Scanner scanner = new Scanner(System.in);
        String number = "";
        int[][] array = createsArrayLetterEqualDigit();
        while (scanner.hasNextLine()) {
            String numberInput = scanner.nextLine();
            for (int i = 0; i < numberInput.length(); i++) {
                char digit = numberInput.toLowerCase().charAt(i);
                if ((Character.isDigit(digit) && (digit - '0') < firstSystem) || (10 < firstSystem && (array[0][0] <= digit && digit <= array[firstSystem - 11][0]))) {
                    number += Character.toString(digit);
                } else {
                    number = "";
                    break;
                }
            }
            if (number.compareTo("") > 0) {
                break;
            } else {
                System.out.println("Sorry. Try again.");
            }
        }
        scanner.close();
        return number;
    }

    public long operationAdd(int digit, int firstSystem, int numberLength, int count) {
        return (long) Math.pow(firstSystem, numberLength - count - 1) * digit;
    }

    public String transformableNumberTo10System(int firstSystem, String number) {
        if (firstSystem != 10) {
            BigInteger bigInteger = BigInteger.valueOf(0);
            int array[][] = createsArrayLetterEqualDigit();
            BigInteger bigAddition;
            for (int i = 0; i < number.length(); i++) {
                int digit = 0;
                char charDigit = number.charAt(i);
                if (Character.isDigit(charDigit)) {
                    digit = number.charAt(i) - '0';
                } else {
                    for (int j = firstSystem - 11; j >= 0; j--) {
                        if (charDigit == array[j][0]) {
                            digit = array[j][1];
                            break;
                        }
                    }
                }
                bigAddition = BigInteger.valueOf(operationAdd(digit, firstSystem, number.length(), i));
                bigInteger = bigInteger.add(bigAddition);
            }
            return bigInteger.toString();
        } else {
            return number;
        }
    }

    public String transformableNumberToRightSystem(String number, int secondSystem) {
        if (secondSystem != 10) {
            BigInteger bigInteger = new BigInteger(number);
            BigInteger zero = new BigInteger("0");
            StringBuilder builder = new StringBuilder();
            int array[][] = createsArrayLetterEqualDigit();
            while (bigInteger.compareTo(zero) > 0) {
                BigInteger divider = BigInteger.valueOf(secondSystem);
                int checkLetter = Integer.valueOf(String.valueOf(bigInteger.mod(divider)));
                if (checkLetter > 9) {
                    for (int j = 0; j < array.length; j++) {
                        if (checkLetter == array[j][1]) {
                            builder.insert(0, Character.toChars(array[j][0]));
                            break;
                        }
                    }
                } else {
                    builder.insert(0, bigInteger.mod(divider));
                }
                bigInteger = bigInteger.divide(divider);
            }
            String result = builder.toString();
            return result.toUpperCase();
        } else {
            return number;
        }
    }

    public static void main(String[] args) {
        NumberSystems start = new NumberSystems();
        int firstSystem = start.firstSystemInput();
        int secondSystem = start.secondSystemInput();
        String number = start.numberInput(firstSystem);
        number = start.transformableNumberTo10System(firstSystem, number);
        number = start.transformableNumberToRightSystem(number, secondSystem);
        System.out.println(number);
    }
}