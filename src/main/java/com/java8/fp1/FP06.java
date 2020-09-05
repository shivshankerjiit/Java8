package com.java8.fp1;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FP06 {
    public static void main(String[] args){
        List<Course> courses =
                Arrays.asList(
                new Course("Spring MVC","Spring",100,125),
                new Course("Spring Boot","Spring",200,225),
                new Course("Music","Art",150,115),
                new Course("Dance","Art",60,215),
                new Course("Spring Fundamental","Spring",300,325)
                );

        Predicate<Course> reviewGreaterThan50 = c -> c.getReviewScore()>50;
        Predicate<Course> reviewGreaterThan150 = c -> c.getReviewScore()>150;
        Predicate<Course> reviewGreaterThan500 = c -> c.getReviewScore()>500;

        System.out.println(courses.stream().allMatch(reviewGreaterThan50));
        System.out.println(courses.stream().anyMatch(reviewGreaterThan150));
        System.out.println(courses.stream().noneMatch(reviewGreaterThan500));

        Comparator<Course> compareByReview = Comparator.comparing(Course::getReviewScore);
        Comparator<Course> compareByReviewReverse = Comparator.comparing(Course::getReviewScore).reversed();
        Comparator<Course> compareByReviewThenPrice = Comparator.comparing(Course::getReviewScore)
                                                                .thenComparing(Course::getPrice);

        System.out.println(
                courses.stream()
                        .sorted(compareByReviewReverse)
                        .collect(Collectors.toList())
        );
        System.out.println(
                courses.stream()
                        .sorted(compareByReviewThenPrice)
                        .collect(Collectors.toList())
        );
        // skip, limit
        System.out.println(
                courses.stream()
                    .skip(2)
                    .limit(1)
                    .collect(Collectors.toList())
        );
        // takeWhile, dropWhile
        //courses.stream().takeWhile(c -> c.getReviewScore()>50).collect(Collectors.toList());
        //courses.stream().dropWhile(c -> c.getReviewScore()>50).collect(Collectors.toList());

        Map<String, List<Course>> groupByCourseMap = courses.stream()
                .collect(Collectors.groupingBy(Course::getSection));
        System.out.println(groupByCourseMap);
        //{
        //  Art=[Course{name='Music', Section='Art', reviewScore=150, price=115}, Course{name='Dance', Section='Art', reviewScore=60, price=215}],
        //  Spring=[Course{name='Spring MVC', Section='Spring', reviewScore=100, price=125}, Course{name='Spring Boot', Section='Spring', reviewScore=200, price=225}, Course{name='Spring Fundamental', Section='Spring', reviewScore=300, price=325}]
        // }
        Map<String, Long> groupBySectionGetCountMap = courses.stream()
                .collect(Collectors.groupingBy(Course::getSection, Collectors.counting()));
        System.out.println(groupBySectionGetCountMap);
        // {Art=2, Spring=3}

        Map<String, Optional<Course>> groupBySectionGetMaxReviewCourseMap = courses.stream()
                .collect(Collectors.groupingBy(
                        Course::getSection,
                        Collectors.maxBy(Comparator.comparing(Course::getReviewScore))
                ));
        System.out.println(groupBySectionGetMaxReviewCourseMap);
        //{Art=Optional[Course{name='Music', Section='Art', reviewScore=150, price=115}],
        // Spring=Optional[Course{name='Spring Fundamental', Section='Spring', reviewScore=300, price=325}]}

        Map<String, List<String>> groupBySectionGetCourseNameList = courses.stream()
                .collect(Collectors.groupingBy(
                        Course::getSection,
                        Collectors.mapping(Course::getName, Collectors.toList())
                ));
        System.out.println(groupBySectionGetCourseNameList);
        // {Art=[Music, Dance], Spring=[Spring MVC, Spring Boot, Spring Fundamental]}

        Optional<Course> maxCourseOptional = courses.stream()
                .max(compareByReviewReverse);
        System.out.println(maxCourseOptional);
        //Optional[Course{name='Dance', Section='Art', reviewScore=60, price=215}]

        Optional<Course> maxEmptyCourseOptional = courses.stream()
                .filter(reviewGreaterThan500)
                .max(compareByReviewReverse);
        System.out.println(maxEmptyCourseOptional);
        //Optional.empty

        Course maxCourse = courses.stream()
                .max(compareByReviewReverse)
                .orElse(new Course("Sports", "Art", 80, 515));
        System.out.println(maxCourse);
        //Course{name='Dance', Section='Art', reviewScore=60, price=215}

        Optional<Course> firstCourse = courses.stream()
                .filter(reviewGreaterThan150)
                .findFirst();
        System.out.println(maxCourse);
        //Course{name='Dance', Section='Art', reviewScore=60, price=215}

        Optional<Course> anyCourse = courses.stream()
                .filter(reviewGreaterThan150)
                .findAny();
        System.out.println(anyCourse);

        System.out.println(
            courses.stream()
                .mapToInt(Course::getReviewScore)
                .sum()
        ); //810

        System.out.println(
                courses.stream()
                    .mapToInt(Course::getReviewScore)
                    .average()
        ); //OptionalDouble[162.0]

        System.out.println(
                courses.stream()
                    .mapToInt(Course::getReviewScore)
                    .max()
        ); //OptionalInt[300]

        System.out.println(
                courses.stream()
                    .mapToInt(Course::getReviewScore)
                    .min()
        ); //OptionalInt[60]

        System.out.println(
                courses.stream()
                    .mapToInt(Course::getReviewScore)
                    .count()
        ); //5

        Map<String,Map<String, Integer>> collectingAndThen = courses.stream()
                .collect(
                        Collectors.groupingBy(Course::getSection,
                                Collectors.groupingBy(Course::getSection,
                                        Collectors.mapping(Course::getName,
                                                Collectors.collectingAndThen(Collectors.toSet(), Set::size)))));
        System.out.println("collectingAndThen->"+collectingAndThen);
        //{2540083={SUCCESS=2}}.SNR Uploaded for : {2540083={SUCCESS=6}}

    }

}
