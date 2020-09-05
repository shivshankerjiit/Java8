package com.java8.fp1;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FP03 {
    public static void main(String[] args){
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);

        Predicate<Integer> integerPredicate = x -> x % 2 == 0;
        Predicate<Integer> integerPredicate1 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer%2 == 0;
            }
        };

        Function<Integer, Integer> integerIntegerFunction = x -> x * x;
        Function<Integer, Integer> integerIntegerFunction1 = new Function<Integer, Integer>(){
            @Override
            public Integer apply(Integer i) {
                return i*i;
            }
        };

        Consumer<Integer> println = System.out::println;
        Consumer<Integer> println1 = new Consumer<Integer>(){
            @Override
            public void accept(Integer n) {
                System.out.println(n);
            }
        };

        BinaryOperator<Integer> sum = Integer::sum;
        BinaryOperator<Integer> sum2 = new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return x+y;
            }
        };

        numbers.stream()
                .filter(integerPredicate1)
                .map(integerIntegerFunction1)
                //.collect(Collectors.toList());
                .forEach(println1);

        numbers.stream()
                .reduce(sum2);
    }
}
