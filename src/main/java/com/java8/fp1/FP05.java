package com.java8.fp1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

public class FP05 {
    public static void main(String[] args){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        Predicate<Integer> evenPredicate = x -> x%2 == 0;
        Function<Integer,Integer> squareFunction = x -> x*x;
        Function<Integer,String> intToString = x -> x+"";
        BinaryOperator<Integer> addBinaryOperator = (x,y) -> x+y;
        UnaryOperator<Integer> multipleUnaryOperator = x -> x*2;
        Consumer<Integer> printConsumer = x -> System.out.println(x);
        Supplier<Integer> constSupplier = () -> 2;

        // function,consumer -> andThen
        System.out.println("function -> andThen="+squareFunction.andThen(x->x+5).apply(3));             //run after
        System.out.println("function -> compose="+squareFunction.compose(x->(Integer) x+3).apply(3));   // run before
        printConsumer.andThen(x->System.out.println("11->"+x+","))
                .andThen(x->System.out.println("22->"+x+",")).accept(3);   // run before
        //predicate -> and , or , negate()
        evenPredicate.and(x->x!=0).or(x->x==2).negate().test(2);


        boolean even = evenPredicate.test(2);
        Integer square = squareFunction.apply(2);
        String str = intToString.apply(2);
        Integer add = addBinaryOperator.apply(1,2);
        Integer mul = multipleUnaryOperator.apply(2);
        printConsumer.accept(2);
        constSupplier.get();

        BiPredicate<Integer,String> biPredicate = (x,y)-> x>10 && y.length() >0;
        BiFunction<Integer,String,String> biFunction = (x,y)-> x+y; //param->Integer,String & return->String
        BiConsumer<Integer,String> biConsumer = (x,y)-> System.out.println(x+y);

        //Int,Long,Double
        IntBinaryOperator intSum = (x,y)->x+y;
        IntUnaryOperator intUnary = x->x*3;
        IntPredicate intPred = x -> x%2 ==0;
        IntFunction intFunction = x -> x*x;
        IntConsumer intConsumer = x -> System.out.println(x);
        IntSupplier intSupplier = () -> 2;
        IntToDoubleFunction iTod = x -> { double y = x; return y;};
        IntToDoubleFunction iTod2 = x -> x;
        IntToLongFunction iToL = x -> x;

        int[] a = {1,2,3};
        Arrays.stream(a) // intStream
                .filter(intPred).map(intUnary).forEachOrdered(System.out::println);

        //param type declaration is optional
        BiFunction<Integer,String,String> p1 = (Integer x,String y) -> x + y + "";
        BiFunction<Integer,String,String> p2 = (x,y) -> x + y + "";

        //Method reference for both -> static and non-static method
        numbers.stream()
                .map(x->x+"")
                .map(String::toUpperCase)       //Method reference non-static method
                .forEach(System.out::println);  //Method reference static method

        //Constructor Reference for default constructor
        Supplier<String> s1 = String::new;
        Supplier<String> s2 = () -> new String();



    }
}
