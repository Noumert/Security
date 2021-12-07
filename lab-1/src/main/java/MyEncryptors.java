import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyEncryptors {
    public static final Map<Character, Integer> alphabetFrequency = Map.ofEntries
            (
                    Map.entry('e', 390395169),
                    Map.entry('t', 282039486),
                    Map.entry('a', 248362256),
                    Map.entry('o', 235661502),
                    Map.entry('i', 214822972),
                    Map.entry('n', 214319386),
                    Map.entry('s', 196844692),
                    Map.entry('h', 193607737),
                    Map.entry('r', 184990759),
                    Map.entry('d', 134044565),
                    Map.entry('l', 125951672),
                    Map.entry('u', 88219598),
                    Map.entry('c', 79962026),
                    Map.entry('m', 79502870),
                    Map.entry('w', 69069021),
                    Map.entry('g', 61549736),
                    Map.entry('y', 59010696),
                    Map.entry('p', 55746578),
                    Map.entry('b', 47673928),
                    Map.entry('f', 32967175),
                    Map.entry('v', 30476191),
                    Map.entry('k', 22969448),
                    Map.entry('x', 5574077),
                    Map.entry('j', 4507165),
                    Map.entry('q', 3649838),
                    Map.entry('z', 2456495)
            );

    public static int getCoef(String text) {
        int result = 0;
        for (Map.Entry<Character, Double> charFrequency : getCharFrequencies(text).entrySet()) {
            Integer letterFrequency = alphabetFrequency.getOrDefault(charFrequency.getKey(), 0);
            if (letterFrequency != 0) {
                result += letterFrequency / 1000 * charFrequency.getValue();
            }
        }
        return result;
    }

    public static Map<Character, Double> getCharFrequencies(String text) {
        return text
                .toLowerCase()
                .chars()
                .mapToObj(ch -> (char) ch)
                .collect(
                        Collectors.toMap(c -> c,
                                c -> StringUtils.countMatches(text, c) * 1000 / (double) text.length(),
                                Double::sum
                        )
                );
    }

    static void xorSingleEncoder(String text) {
        Map<Character, String> result = getAllXorShifts(text);
        System.out.println("Most possible variant:" + getBestKey(result));

//        result.forEach((key, value) -> System.out.println(key + ":" + value + "coef:" + getCoef(value)));
    }

    private static Map.Entry<Character, String> getBestKey(Map<Character, String> result) {
        return result.entrySet().stream().max(Comparator.comparing(m -> getCoef(m.getValue()))).get();
    }

    public static void xorRepeatingEncoder(String text) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Index of coincidence analysis");
        indexOfCoincidenceAnalysis(text);
        System.out.print("key length: ");
        int keyLength = scanner.nextInt();


        List<String> rows = Arrays.stream(text.split("(?<=\\G.{" + keyLength + "})")).toList();
        List<String> columns = IntStream
                .range(0, keyLength)
                .mapToObj(i -> rows.stream().map(r -> {
                            if (r.length() > i) {
                                return r.toCharArray()[i];
                            } else {
                                return null;
                            }
                        })
                        .toList())
                .map(columnLetters -> {
                    StringBuilder builder = new StringBuilder(columnLetters.size());
                    for (Character ch : columnLetters) {
                        builder.append(ch);
                    }
                    return builder.toString();
                }).toList();

        List<Map<Character, String>> columnsXorShifts = columns
                .stream().map(str -> getAllXorShifts(str).entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .toList();

        Map<String, String> textXorShifts = new HashMap<>();

        String bestKey = getBestKeyR(columnsXorShifts);
        System.out.println("Most possible key:" + bestKey);
        System.out.println("Real key: L0lL0lL0l");
        System.out.print("Most possible text: ");
        String decodedText = xorEncoderR(text, bestKey);
        System.out.println(decodedText);
        System.out.print("Real text: ");
        decodedText = xorEncoderR(text, "L0lL0lL0l");
        System.out.println(decodedText);

    }

    private static String getBestKeyR(List<Map<Character, String>> columnsXorShifts) {
        List<Character> chars = columnsXorShifts.stream()
                .map(shifts -> shifts.entrySet()
                        .stream().max(Comparator.comparing(m -> {
                                    System.out.println(m.getKey() + "    coef: " + getCoef(m.getValue()) + " value: " + m.getValue());
                                    return getCoef(m.getValue());
                                }
                        )).get().getKey())
                .toList();

        columnsXorShifts.stream()
                .map(shifts -> shifts.entrySet()
                        .stream().sorted(Comparator.comparing(m -> getCoef(m.getValue()))).toList())
                .forEach(maps -> {
                    for (Map.Entry<Character, String> m :
                            maps) {
                        System.out.println(m.getKey() + "    coef: " + getCoef(m.getValue()) + " value: " + m.getValue());
                    }
                });

        List<Character> chars2 = columnsXorShifts.stream()
                .map(shifts -> shifts.entrySet()
                        .stream().max(Comparator.comparing(m -> getCoef(m.getValue()))).get().getKey())
                .toList();
        StringBuilder builder = new StringBuilder(chars.size());
        for (Character ch : chars) {
            builder.append(ch);
        }

        return builder.toString();
    }

    private static void indexOfCoincidenceAnalysis(String text) {
        List<Double> list = new ArrayList<>();
        for (int i = 1; i < text.length(); i++) {
            String shiftedText = shift(text, i);
            int coincidenceCount = 0;
            for (int j = 0; j < text.length(); j++) {
                if (shiftedText.charAt(j) == text.charAt(j)) {
                    coincidenceCount++;
                }
            }
            double indexOfCoincidence = (double) coincidenceCount / text.length();
            list.add(indexOfCoincidence);
            System.out.format("Index of coincidence with shift = %d :%.3f, %d coincidences\n", i, indexOfCoincidence, coincidenceCount);
        }
        list.sort(Comparator.comparing(Double::doubleValue));
        list.forEach(System.out::println);
    }


    public static String shift(String s, int count) {
        return s.substring(count, s.length()) + s.substring(0, count);
    }

    private static Map<Character, String> getAllXorShifts(String text) {
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


    public static String xorEncoderR(String text, String key) {
        ;
        var result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append((char) (text.charAt(i) ^ key.charAt(i % key.length())));
        }
        return result.toString();
    }
}
