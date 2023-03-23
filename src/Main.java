import java.util.*;

public class Main {

    public static String[] calc(String inputString) {
        String[] calc_inputString = inputString.split(" ");
        if (calc_inputString.length == 1) {
            throw new RuntimeException("строка не является математической операцией");
        }
        if (calc_inputString.length != 3) {
            throw new RuntimeException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        } else {
            return calc_inputString;
        }
    }

    private static boolean its_an_arabic_numbers = true;

    public static void main(String[] args) {
        Scanner inputString_a_value = new Scanner(System.in);
        System.out.print("Введите выражение, разделяя каждый символ пробелом:");
        String inputString = inputString_a_value.nextLine();
        while (!inputString.isEmpty()) {
            String[] calc_inputString;
            calc_inputString = calc(inputString);
            String operation = calc_inputString[1];
            Number values;
            int value1 = 1;
            int value2 = 1;
            // Переводим в int. Если введены римские, выкинет исключение
            try {
                value1 = Integer.parseInt(calc_inputString[0]);
                value2 = Integer.parseInt(calc_inputString[2]);
                values = new Arabic(value1, value2);
            } catch (NumberFormatException e) {
                its_an_arabic_numbers = false;
                //System.out.println("Введены римские цифры");
                values = new Romes(calc_inputString[0], calc_inputString[2]);
            }
            if (value1 > 0 && value1 <= 10 && value2 > 0 && value2 <=  10) {
                switch (operation) {
                    case "+" -> values.sum();
                    case "-" -> values.sub();
                    case "/", ":" -> values.div();
                    case "*", "x" -> values.mul();
                    default -> throw new RuntimeException("Неверный оператор");
                }

                if (its_an_arabic_numbers) {
                    System.out.println("Ответ: " + values.getResult());
                } else {
                    System.out.println("Ответ: " + values.getStringResult());
                }
            }
            else {
                throw new RuntimeException("Введите числа от 1 до 10");
            }
            System.out.println();
            System.out.print("Введите следующее выражение: ");
            inputString = inputString_a_value.nextLine();
        }
        System.out.println("Вы вышли из калькулятора");

    }
}

abstract class Number {
    public abstract void sum();

    public abstract void sub();

    public abstract void div();

    public abstract void mul();

    public abstract int getResult();
    public abstract String getStringResult();
}


class Romes extends Number {
    private final int romes_value1_int;
    private final int romes_value2_int;
    private int result_int;
    private String result_string;

    Romes(String value1, String value2) {
        this.romes_value1_int = convert_to_int(value1);
        this.romes_value2_int = convert_to_int(value2);
    }
    private String convert_result_to_Romes(int number) {
        try {
            return RomanArabicConverter.arabicToRoman(number);
        } catch (RuntimeException exc) {
            throw new RuntimeException("В римской системе нет отрицательных чисел!");
        }
    }

    @Override
    public void sum() {
        result_int = romes_value1_int + romes_value2_int;
        result_string = convert_result_to_Romes(result_int);
    }

    @Override
    public void sub() {
        result_int = romes_value1_int - romes_value2_int;
        result_string = convert_result_to_Romes(result_int);
    }

    @Override
    public void div() {
        result_int = romes_value1_int / romes_value2_int;
        result_string = convert_result_to_Romes(result_int);
    }

    @Override
    public void mul() {
        result_int = romes_value1_int * romes_value2_int;
        result_string = convert_result_to_Romes(result_int);
    }

    @Override
    public int getResult() {
        return result_int;
    }
    public String getStringResult() {
        return result_string;
    }

    private int convert_to_int(String romes_value){
        char[] value_char = romes_value.toCharArray();
        int[] values_int = new int[value_char.length];
        for (int i = 0; i < value_char.length; i++) {
            switch (value_char[i]) {
                case 'I' -> values_int[i] = 1;
                case 'V' -> values_int[i] = 5;
                case 'X' -> values_int[i] = 10;
                default ->
                        throw new RuntimeException("используются одновременно разные системы счисления");
            }
        }
        int result = values_int[0];
        for (int i = 0; i < values_int.length && values_int.length > i + 1; i++) {
            if (values_int[i] >= values_int[i+1]) {
                result += values_int[i+1];
            } else if (values_int[i] < values_int[i+1]) {
                result = result + values_int[i+1] - 2;
            }
        }
        return result;
    }
}

class Arabic extends Number {

    private final int value1;
    private final int value2;
    private int result;

    Arabic(int value1, int value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public void sum() {
        this.result = value1 + value2;
    }

    public void sub() {
        this.result = value1 - value2;
    }

    public void div() {
        this.result = value1 / value2;
    }

    public void mul() {
        this.result = value1 * value2;
    }

    @Override
    public int getResult() {
        return result;
    }

    @Override
    public String getStringResult() {
        return "" + result;
    }
}