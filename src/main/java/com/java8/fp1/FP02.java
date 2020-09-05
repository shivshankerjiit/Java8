package com.java8.fp1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FP02 {
    public static void main(String[] args){
        List<Integer> numbers = Arrays.asList(10,2,3,4,5);
        Integer sum = printSum(numbers);
        System.out.println(sum);

        Integer min_value = minimum(numbers);
        System.out.println(min_value);

        Integer max_value = maximum(numbers);
        System.out.println(max_value);

        Integer squareSum = printSquareSum(numbers);
        System.out.println(squareSum);

        Integer oddSum = printOddSum(numbers);
        System.out.println(oddSum);

        numbers.stream()
                .distinct()
                //.sorted()
                //.sorted(Comparator.naturalOrder())
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        List<String> courses = Arrays.asList("Spring","Spring AOP","Docker","Javascript","Fundamental Spring");
        courses.stream()
                //.distinct()
                //.sorted()
                .sorted(Comparator.comparing(c -> c.length()))
                .forEach(System.out::println);

        List<Integer> evenNumbers = numbers.stream()
                                        .map(x -> x*x)
                                        .collect(Collectors.toList());
        System.out.println(evenNumbers);
        List<String> courseList = courses.stream()
                                        .filter(x -> x.length() > 6)
                                        .collect(Collectors.toList());
        System.out.println(courseList);
    }

    private static Integer printOddSum(List<Integer> numbers) {
        return numbers.stream()
                .filter(x->x%2 == 1)
                .reduce(0,Integer::sum);
    }

    private static Integer printSquareSum(List<Integer> numbers) {
        return numbers.stream()
                .map(x -> x*x)
                .reduce(0,Integer::sum);
    }

    private static Integer minimum(List<Integer> numbers) {
        return numbers.stream()
                .reduce(Integer.MAX_VALUE, (a,n) -> a>n ? n : a);
    }

    private static Integer maximum(List<Integer> numbers){
        return numbers.stream()
                    .reduce(Integer.MIN_VALUE, (a,n) -> a>n ? a : n);
    }

    private static Integer printSum(List<Integer> numbers) {
        return numbers.stream()
                .reduce(0,Integer::sum);
                //.reduce(0,(x,y) -> x+y);
                //.reduce(0,FP02::sum);
    }

    private static Integer sum(Integer acc, Integer nextInt) {
        return acc + nextInt;
    }
}
