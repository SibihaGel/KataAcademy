import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;



public enum RomesInternal {
    I(1), IV(4), V(5), IX(9), X(10),
    XL(40), L(50), XC(90), C(100),
    CD(400), D(500), CM(900), M(1000);

    private int value;

    RomesInternal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static List<RomesInternal> getReverseSortedValues() {
        return Arrays.stream(values())
                .sorted(Comparator.comparing((RomesInternal e) -> e.value).reversed())
                .collect(Collectors.toList());
    }
}
    class RomanArabicConverter {

        public static int romanToArabic(String input) {
            String romanNumeral = input.toUpperCase();
            int result = 0;

            List<RomesInternal> romanNumerals = RomesInternal.getReverseSortedValues();

            int i = 0;

            while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
                RomesInternal symbol = romanNumerals.get(i);
                if (romanNumeral.startsWith(symbol.name())) {
                    result += symbol.getValue();
                    romanNumeral = romanNumeral.substring(symbol.name().length());
                } else {
                    i++;
                }
            }
            if (romanNumeral.length() > 0) {
                throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
            }

            return result;
        }

        public static String arabicToRoman(int number) {
            if ((number <= 0) || (number > 4000)) {
                throw new IllegalArgumentException(number + " is not in range (0,4000]");
            }

            List<RomesInternal> romanNumerals = RomesInternal.getReverseSortedValues();

            int i = 0;
            StringBuilder sb = new StringBuilder();

            while (number > 0 && i < romanNumerals.size()) {
                RomesInternal currentSymbol = romanNumerals.get(i);
                if (currentSymbol.getValue() <= number) {
                    sb.append(currentSymbol.name());
                    number -= currentSymbol.getValue();
                } else {
                    i++;
                }
            }
            return sb.toString();
        }
    }
