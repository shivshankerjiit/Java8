package com.java8.fp1;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FP04 {
    public static void main(String[] args){
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);

        // RC->refactor->introduce variable
        // RC->refactor->inline
        //Predicate<Integer> integerPredicate = x1 -> x1 % 2 == 0;

          // RC -> refactor -> extract method
/*        numbers.stream()
                .filter(x->x%2 == 0)
                .forEach(System.out::println);
*/

        filterAndPrint(numbers, x1 -> x1 % 2 == 0);
        filterAndPrint(numbers, x -> x % 2 != 0);

        List<Integer> squareList = getMultipleOfList(numbers, x -> x*x);
        List<Integer> cubeList = getMultipleOfList(numbers, x -> x*x*x);

    }

    public static List<Integer> getMultipleOfList(List<Integer> numbers, Function<Integer, Integer> function) {
        return numbers.stream()
        .map(function)
        .collect(Collectors.toList());
    }

    public static void filterAndPrint(List<Integer> numbers, Predicate<Integer> predicate) {
        numbers.stream()
                .filter(predicate)
                .forEach(System.out::println);
    }


}
