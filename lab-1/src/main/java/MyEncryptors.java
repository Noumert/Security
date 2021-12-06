import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyEncryptors {

//    public static final Map<Character, Integer> alphabetFrequency = Map.ofEntries
//            (
//                    Map.entry('e', 390395169),
//                    Map.entry('t', 282039486),
//                    Map.entry('a', 248362256),
//                    Map.entry('o', 235661502),
//                    Map.entry('i', 214822972),
//                    Map.entry('n', 214319386),
//                    Map.entry('s', 196844692),
//                    Map.entry('h', 193607737),
//                    Map.entry('r', 184990759),
//                    Map.entry('d', 134044565),
//                    Map.entry('l', 125951672),
//                    Map.entry('u', 88219598),
//                    Map.entry('c', 79962026),
//                    Map.entry('m', 79502870),
//                    Map.entry('f', 72967175),
//                    Map.entry('w', 69069021),
//                    Map.entry('g', 61549736),
//                    Map.entry('y', 59010696),
//                    Map.entry('p', 55746578),
//                    Map.entry('b', 47673928),
//                    Map.entry('v', 30476191),
//                    Map.entry('k', 22969448),
//                    Map.entry('x', 5574077),
//                    Map.entry('j', 4507165),
//                    Map.entry('q', 3649838),
//                    Map.entry('z', 2456495)
//            );
//
//    public static Double getCoef(String text) {
//        Double result = 0.0;
//        for (Map.Entry<Character, Double> charFrequency : getCharFrequencies(text).entrySet()) {
//            var letterFrequency = alphabetFrequency.getOrDefault(charFrequency.getKey(), 0);
//            if (letterFrequency != 0) {
//                result += letterFrequency * charFrequency.getValue();
//            }
//        }
//        return result;
//    }

//    public static Map<Character, Double> getCharFrequencies(String text) {
//        return text
//                .toLowerCase()
//                .chars()
//                .mapToObj(ch -> (char) ch)
//                .collect(
//                        Collectors.toMap(c -> c,
//                                c -> StringUtils.countMatches(text, c) * 1000 / (double) text.length()
//                        )
//                );
//    }

    static void xorSingleEncoder(String text) {
        Map<Character, String> result = GetAllXorShifts(text);

        result.forEach((key, value) -> System.out.println(key + ":" + value));
    }

    private static Map<Character, String> GetAllXorShifts(String text) {
        List<Character> keys = IntStream.range(0, 128)
                .mapToObj(digits -> (char) digits)
                .toList();

        Map<Character, String> shifts = new HashMap<>();
        for (Character key : keys) {
            if (!Character.isLetterOrDigit(key)) {
                continue;
            }
            shifts.put(key, xorEncoder(text, key));
        }
        return shifts;
    }

    static String xorEncoder(String text, char key) {
        StringBuilder result = new StringBuilder();
        text.chars().forEach(ch -> {
            result.append((char) (ch ^ key));
        });
        return result.toString();
    }

    static boolean isEnglishLetter(char c) {
        char minValue = 'A';
        char maxValue = 'Z';
        return c >= minValue && c <= maxValue;
    }

}
