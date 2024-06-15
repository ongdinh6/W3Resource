package lambdaExpression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

class ExercisesTest {

    @Test
    void testCalculateSumOfTwoNumbers() {
        BiFunction<Integer, Integer, Integer> sum = Integer::sum;

        Assertions.assertEquals(5, sum.apply(2, 3));
    }

    @Test
    void testValidateStringEmpty() {
        Predicate<String> isEmpty = String::isEmpty;

        assertTrue(isEmpty.test(""));
        assertFalse(isEmpty.test("Non-empty string"));
    }

    @Test
    void testConvertListOfStringToLowercaseAndUppercase() {
        List<String> lsLowercaseStrings = List.of("java", "javascript", "nodejs", "python");
        List<String> lsUppercaseStrings = List.of("JAVA", "JAVASCRIPT", "NODEJS", "PYTHON");

        BiFunction<List<String>, Boolean, List<String>> convertStrings = (ls, toLowerCase) -> ls
            .stream()
            .map(toLowerCase ? String::toLowerCase : String::toUpperCase)
            .collect(Collectors.toList());


        Assertions.assertEquals(lsLowercaseStrings, convertStrings.apply(lsUppercaseStrings, true));
        Assertions.assertEquals(lsUppercaseStrings, convertStrings.apply(lsLowercaseStrings, false));
    }

    @Test
    void testFilterOutOddAndEvenNumbersFromListIntegers() {
        List<Integer> lsIntegers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        BiFunction<List<Integer>, Boolean, List<Integer>> filteredLsIntegers = (ls, isOddNumbers) -> ls
            .stream()
            .filter(number -> isOddNumbers == (number % 2 != 0))
            .collect(Collectors.toList());

        Assertions.assertEquals(List.of(1, 3, 5, 7, 9), filteredLsIntegers.apply(lsIntegers, true));
        Assertions.assertEquals(List.of(2, 4, 6, 8, 10), filteredLsIntegers.apply(lsIntegers, false));
    }

    @Test
    void testSortListOfStringByAlphabet() {
        List<String> lsStrings = List.of("A", "D", "B", "E", "F", "C");

        BiFunction<List<String>, Boolean, List<String>> sortedListStrings = (ls, isAsc) -> ls
            .stream()
            .sorted((a, b) -> {
                if (isAsc) return a.compareTo(b);
                return b.compareTo(a);
            })
            .collect(Collectors.toList());

        Assertions.assertEquals(List.of("A", "B", "C", "D", "E", "F"), sortedListStrings.apply(lsStrings, true));
        Assertions.assertEquals(List.of("F", "E", "D", "C", "B", "A"), sortedListStrings.apply(lsStrings, false));
    }

    @Test
    void testCalculateAverageListOfDoubles() {
        List<Double> lsDoubles = List.of(0.5d, 3.0d, 2.5d);

        Assertions.assertEquals(2.0d, lsDoubles.stream().reduce(0.0d, Double::sum) / lsDoubles.size());
    }

    @Test
    void testRemoveDuplicateElementsInListOfIntegers() {
        List<Integer> lsIntegers = List.of(1, 2, 3, 3, 4, 2);

        Assertions.assertEquals(List.of(1, 2, 3, 4), lsIntegers.stream().distinct().collect(Collectors.toList()));
    }

    @Test
    void testCalculateFactorialOfAnIdentifiedNumber() {
        long number = 3L;
        LongUnaryOperator factorial = (no) -> {
            long result = 1;
            for (int i = 1; i <= no; i++) {
                result *= i;
            }
            return result;
        };

        Assertions.assertEquals(6, factorial.applyAsLong(number));
    }

    @Test
    void testValidateNumberIsAPrime() {
        Predicate<Integer> prime = (no) -> {
            if (no <= 1) {
                return false;
            }
            for (int i = 2; i <= Math.sqrt(no); i++) {
                if (no%i == 0) {
                    return false;
                }
            }
            return true;
        };

        assertTrue(prime.test(7));
        assertFalse(prime.test(4));
    }

    @Test
    void testConcatTwoStrings() {
        BiFunction<String, String, String> concat = String::concat;
        Assertions.assertEquals("Hello, Java", concat.apply("Hello, ", "Java"));
    }

    @Test
    void testFindMaximumAndMinimumValue() {
        BiFunction<List<Integer>, BinaryOperator<Integer>, Integer> value = (ls, fn) -> ls.isEmpty() ? null : ls
            .stream()
            .reduce(ls.get(0), fn);

        Assertions.assertEquals(5, value.apply(List.of(1, 4, 3, 5, 2), Integer::max));
        Assertions.assertEquals(1, value.apply(List.of(2, 4, 3, 5, 1), Integer::min));
        assertNull(value.apply(List.of(), Integer::min));
    }

    @Test
    void testMultiplyAndSumElementsInListOfIntegers() {
        BiFunction<List<Integer>, Boolean, Integer> value = (ls, isMultiply) -> ls.isEmpty() ? null : ls
            .stream()
            .reduce(isMultiply ? 1 : 0, isMultiply ? (a, b) -> a*b : Integer::sum);

        Assertions.assertEquals(15, value.apply(List.of(1, 4, 3, 5, 2), false));
        Assertions.assertEquals(120, value.apply(List.of(2, 4, 3, 5, 1), true));
        assertNull(value.apply(List.of(), false));
    }

    @Test
    void testCountWordsInASentence() {
        Function<String, Integer> count = (str) -> str.split("\\s+").length;
        Assertions.assertEquals(3, count.apply("Welcome to Java"));
    }

    @Test
    void testValidateStringPalindrome() {
        Predicate<String> palindrome = (str) -> {
            StringBuilder stringBuilder = new StringBuilder(str);
            return str.contentEquals(stringBuilder.reverse());
        };

        assertTrue(palindrome.test("radar"));
        assertFalse(palindrome.test("Madam"));
        assertFalse(palindrome.test("green"));
    }

    @Test
    void testSumOfSquareOddAndEvenNumbers() {
        BiFunction<List<Integer>, Predicate<Integer>, Integer> sum = (ls, fn) -> ls.isEmpty() ? null : ls
            .stream()
            .filter(fn)
            .map(no -> no*no)
            .reduce(0, Integer::sum);

        Assertions.assertEquals(10, sum.apply(List.of(1, 2, 3, 4), (a) -> a%2 != 0));
        Assertions.assertEquals(20, sum.apply(List.of(1, 2, 3, 4), (a) -> a%2 == 0));
        assertNull(sum.apply(List.of(), (a) -> a%2 == 0));
    }

    @Test
    void testValidateStringContainsASpecificWord() {
        BiFunction<String, String, Boolean> contains = String::contains;

        assertTrue(contains.apply("Hello Java", "Java"));
        assertFalse(contains.apply("Hello Java", "Python"));
        assertFalse(contains.apply("", "Hello"));
    }

    @Test
    void testFindTheLengthOfLongestAndSmallestStringInListOfStrings() {
        BiFunction<List<String>, BinaryOperator<Integer>, Integer> find = (ls, fn) -> ls.isEmpty() ? null : ls
            .stream()
            .map(String::length)
            .reduce(ls.get(0).length(), fn);

        Assertions.assertEquals(7, find.apply(List.of("Hello", "world", ".", "Welcome", "to", "Java"), Integer::max));
        Assertions.assertEquals(1, find.apply(List.of("Hello", "world", ".", "Welcome", "to", "Java"), Integer::min));
        assertNull(find.apply(List.of(), Integer::max));
    }

    @Test
    void testValidateANumberIsAPerfectSquare() {
        Predicate<Integer> prefectSquare = (no) -> no == 1 || Math.sqrt(no) % 2 == 0 || Math.sqrt(no) % 3 == 0;

        assertTrue(prefectSquare.test(0));
        assertTrue(prefectSquare.test(1));
        assertTrue(prefectSquare.test(4));
        assertTrue(prefectSquare.test(9));
        assertFalse(prefectSquare.test(3));
        assertFalse(prefectSquare.test(6));
        assertFalse(prefectSquare.test(7));
    }

    @Test
    void testFindSecondLargestAndSmallestElementInArray() {
        BiFunction<List<Integer>, Boolean, Integer> findSecond = (ls, findMax) -> ls
            .stream()
            .distinct()
            .sorted(findMax ? Comparator.reverseOrder() : Comparator.naturalOrder())
            .skip(1)
            .findFirst()
            .orElse(null);

        Assertions.assertEquals(9, findSecond.apply(List.of(1, 10, 2, 9, 7, 10, 9), true));
        Assertions.assertEquals(2, findSecond.apply(List.of(1, 10, 2, 9, 7, 10, 1), false));
    }

    @Test
    void testCalculateSumOfPrimeNumbersInRange() {
        Function<Integer, Integer> sumOfPrime = (endExclusive) -> IntStream.range(0, endExclusive)
            .filter(no -> {
                if (no <= 1) {
                    return false;
                }
                for (int i = 2; i <= Math.sqrt(no); i++) {
                    if (no%i == 0) {
                        return false;
                    }
                }
                return true;
            })
            .reduce(0, Integer::sum);

        Assertions.assertEquals(10, sumOfPrime.apply(6));
    }

    @Test
    void testValidateListStringsWithTextTransform() {
        BiPredicate<List<String>, String> check = (ls, type) -> !ls.isEmpty() && ls
            .stream()
            .allMatch(it -> {
                if (Objects.equals(type, "lowercase")) {
                    return Objects.equals(it, it.toLowerCase());
                } else if (Objects.equals(type, "uppercase")) {
                    return Objects.equals(it, it.toUpperCase());
                } else {
                    return true;
                }
            });

        assertTrue(check.test(List.of("java", "python", "node"), "lowercase"));
        assertTrue(check.test(List.of("JAVA", "PYTHON", "NODE"), "uppercase"));
        assertTrue(check.test(List.of("java", "Python", "node"), "mix"));
    }

    @Test
    void testCalculateAverageLengthOfListStrings() {
        Function<List<String>, Double> averageOfLength = (ls) -> ls
            .stream()
            .mapToInt(String::length)
            .average()
            .orElse(0.0D);

        Assertions.assertEquals(3.0D, averageOfLength.apply(List.of("hel", "llo", "sun")));
        Assertions.assertEquals(0.0D, averageOfLength.apply(List.of()));
    }

    @Test
    void testFindFactoryPrimeNumber() {
        Function<Long, Long> factoryNumber = (no) -> {
            List<Long> factors = LongStream
                    .rangeClosed(2, (long) Math.sqrt(no))
                    .boxed()
                    .collect(Collectors.toList());
            Collections.reverse(factors);

            long firstFactor = factors
                .stream()
                .filter(factor -> no%factor == 0 && isPrime(factor))
                .findFirst()
                .orElse(no);
            if (Objects.equals(firstFactor, no)) {
                return no;
            } else {
                return (no/firstFactor)%2 != 0 ? (no/firstFactor) : firstFactor;
            }
        };

        Assertions.assertEquals(11L, factoryNumber.apply(176L));
        Assertions.assertEquals(3L, factoryNumber.apply(36L));
        Assertions.assertEquals(29L, factoryNumber.apply(87L));
        Assertions.assertEquals(29L, factoryNumber.apply(29L));
    }

    boolean isPrime(long number) {
        return LongStream
            .rangeClosed(2, (long) Math.sqrt(number))
            .boxed()
            .allMatch(i -> number%i != 0);
    }

    @Test
    void testRepresentationIntegerAsBinary() {
        Function<Integer, String> represent = Integer::toBinaryString;

        Assertions.assertEquals("10", represent.apply(2));
        Assertions.assertEquals("10010", represent.apply(18));
        Assertions.assertEquals("10000000", represent.apply(128));
    }

}