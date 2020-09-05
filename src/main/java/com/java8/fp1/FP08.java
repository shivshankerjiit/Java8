package com.java8.fp1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class FP08 {
    public static void main(String[] args) {
        // 1 -> null list, 2 -> null element
        // storing state of previous step -> while loop
        List<Course> courses1 =
                Arrays.asList(
                        new Course("Spring MVC","Spring",100,125),
                        null,
                        new Course("Spring Boot","Spring",200,225)
                );
        List<Course> courses2 =
                Arrays.asList(
                        new Course("Dance","Art",60,215)
                );
        List<List<Course>> listOfList = Arrays.asList(courses1,null,courses2);
        //maintaining state in stream
        //  1) has to be final
        final int[] positionState = {0};
        IntStream.range(0,listOfList.size())
                .map(pos -> {
                    positionState[0] = pos;
                    return pos;
                })
                .mapToObj(pos -> listOfList.get(pos))
                .map(Optional::ofNullable)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(courseList -> courseList.size() > 0)
                .flatMap(List::stream)
                .parallel()
                .map(Optional::ofNullable)
                //.filter(optional -> optional.equals(courses1.get(0)))
                .filter(Optional::isPresent)
                //.map(optional -> optional.orElseGet(Course::new))
                //.map(optional -> optional.orElse(new Course()))
                //.map(optional -> optional.orElseThrow(RuntimeException::new))
                .map(Optional::get)
                .sorted(Comparator.comparing(Course::getName).thenComparing(Course::getPrice).reversed())
                .forEachOrdered(
                        course-> System.out.println(
                        "index->"+positionState[0]
                                +",course->"+course.getName()
                ));
    }
}
