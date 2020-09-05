package com.java8.fp1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.*;

public class FP07 {
    public static void main(String[] args){
        //1 - Integer Stream , list.stream()
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);
        Stream<Integer> integerStream1 = numbers.stream();

        //2 - Integer Stream , Stream.of()
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);

        //3 - primitive stream , Arrays.stream()
        int[] num = {1,2,3,4,5,6};
        IntStream primitiveStream = Arrays.stream(num);

        //4 - primitive stream , IntStream.range(1,5)
        IntStream intStreamRange = IntStream.range(1, 10);
        IntStream.range(1, 10).sum();
        IntStream.rangeClosed(1,10);

        //5 - primitive stream , IntStream.iterate(0 , e->e+1)
        IntStream intStreamRangeItr = IntStream.iterate(1,e -> e +2 ).limit(10);
        //peek() help to look(run function) at stream without doing anything to stream
        IntStream.iterate(1,e -> e +2 ).limit(10).peek(System.out::println).sum();
        //Error primitive Stream can't call collect() need to do boxed() before applying collect()
        // IntStream.range(1,10).collect(Collectors.toList());
        List<Integer> intList = IntStream.range(1, 10).boxed().collect(Collectors.toList());
        DoubleStream.iterate(1,e->e+1).limit(10).boxed().collect(Collectors.toList());

        //BigInteger calculation
        System.out.println(
                LongStream.range(1,100).reduce(1,(x,y)->x*y)
        ); //0
        System.out.println(
                LongStream.range(1,100).mapToObj(BigInteger::valueOf).reduce(BigInteger.ONE,BigInteger::multiply)
        );//933262154439441526816992388562667004907159682643816214685929638952175999932299156089414639761565182862536979208272237582511852109168640000000000000000000000

        //***************Revise*********

        //intStream.sum()           ->6
        //            .count()      ->3
        //            .average()    ->OptionalDouble[2]
        //            .max()        ->OptionalInt[3]
        //            .min()        ->OptionalInt[1]
        //******************
        //stream.max(Comparator)    ->Optional[...]
        //      .min(Comparator)    ->Optional[...]
        //      .findAny()
        //      .findFirst()
        //******************
        //Comparator -> Comparator.comparing(Function<T,K>).thenComapring(Function<T,K>).reversed()
        //            -> Comparator.naturalOrder()
        //            -> Comparator.reverseOrder()
        //stream.sorted(Comparator)
        //stream.skip(1).limit(1)
        //stream.collect(Collectors.groupingBy(Function<T,K>, Collectors.mapping(Course::getName, Collectors.toList())  ->{Art=[Music, Dance], Spring=[Spring, Spring Boot]}
        //                                                    Collectors.maxBy(Comparator)                              ->{Art=Optional[Course{name='Music', Section='Art'}],Spring=Optional[Course{name='Spring',Section='Spring'}]}
        //                                                    Collectors.counting()                                     ->{Art=2, Spring=3}
        //*********************
        //IntStream.range(1,10)
        //IntStream.rangeClosed(1,10)
        //IntStream.iterate(1,e->e+2).limit(10).sum()/reduce()/count()....
        //                                      .peek().sum()
        //                                      .boxed().collect(...)
        //                                      .mapToObj(BigInteger::valueOf).reduce(BigInteger.ONE,BigInteger::multiply)
        //************************
        //courses.stream()
        //      .map(Optional::ofNullable)
        //      .filter(Optional::isPresent)
        //      .map(Optional::get)
        //      .map(c->c.tolowercase())
        //      .collect(Collectors.toList());
        //  Optional -> optional.orElse(default) -> less efficient as called always
        //              optional.orElseGet(Supplier) -> more efficient called when Optional.empty
        //                      .orElseThrow(Runtimeexception::new)
        //                      .get()
        //  Optional.of(-)
        //          .ofNullable(-)
        //          .empty()
        // Optional.map(-)
        //          .filter(-)
        //          .isPresent(-)
        //          .ifPresent(-)
        //          .equals(-)
        //          .equals(Optional.Empty)
        //
        // (Optional.empty).anyOperation -> Optional.empty
        //**************************************************
        //Collector ->  toList
        //              toMap(keyMapper,valueMapper)
        //              toCollection(Linkedlist::new)
        //              CollectionAndThen(toList(),ImmutableList::copyOf)
        //              joining()
        //              counting()
        //              maxBy(comparator)
        //Collector->   groupingBy(key,collector)
        //              partitioningBy(predicate)
        //              teeing(collector1,collector2,(a,b)->{})
        //              mappingBy(-,collector)
        //*******************************************************
        //stream() ->   sum()/max()/max(comparator)/count()/boxed().collect()
        //         ->   sorted(c),limit(1),skip(1)
        //         ->   reduce() , peek()
        //         ->   mapToInt() , mapToObj()
        //         ->   findFirst()/findAny()
        //         ->   matchAll()/matchNone()/matchAny()
        //         ->   forEach(), forEachOrdered() , parallel()
        //**************************************************************
        // predicate.test(x) , -> and , or , negate()
        // consumer.accept(),  -> andThen()
        // supplier.get()
        // binaryOperator/function.apply() -> andThen() ,compose()
        //******************************************************************
        //map.entrySet().stream().map(entry->e.getKey,e.getValue)
        


    }
}
