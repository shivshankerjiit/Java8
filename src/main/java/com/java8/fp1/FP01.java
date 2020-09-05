package com.java8.fp1;

import java.util.*;
import java.util.stream.Collectors;

public class FP01 {
    public static void main(String[] args){
        System.out.println("Welcome");
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        List<String> courses = Arrays.asList("Spring","Spring AOP","Docker","Javascript","Fundamental Spring");
        printAll(list);
        printAllCourses(courses);
        printCourseLength(courses);
        testOptionStream();
        testOptionalEmpty();
        isEmptyOrNullString("  ");
    }

    private static void printCourseLength(List<String> courses) {
        courses.stream()
                .map(course -> course.length())
                .forEach(FP01::print);
    }

    private static void printAllCourses(List<String> courses) {
        courses.stream()
                .filter( course -> course.contains("Spring"))
                .filter( course -> course.length() > 6)
                .forEach(FP01::printCourse);
    }

    private static void printCourse(String course) {
        System.out.println(course);
    }

    private static void printAll(List<Integer> list) {
        list.stream()
                //.filter(FP01::isEven)
                .filter( number -> number%2 == 1)
                .forEach(FP01::print);
    }

    private static boolean isEven(Integer number) {
        return number%2 == 0;
    }

    private static void print(Integer number){
        System.out.println(number);
    }

    private static void testOptionStream(){
        System.out.println("****************Optional Stream *************");
        List<String> coursesWithNull = Arrays.asList(null,"Spring AOP","Docker","Javascript","Fundamental Spring");

        coursesWithNull.stream()
                .map(c->Optional.ofNullable(c))
                .map(c->c.orElseGet(()->""))
                .map(c->c.length())
                .forEach(System.out::println);

        coursesWithNull.stream()
                .map(Optional::ofNullable)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(String::length)
                .forEach(System.out::println);

        coursesWithNull.stream()
                .map(Optional::ofNullable)
                .map(c->c.map(c1->c1.length()))
                .forEach((System.out::println));

        List<String> strings2 = Optional.ofNullable(coursesWithNull)
                .filter(c -> c.size() > 0)
                .orElse(new ArrayList<>());
    }

    private static boolean isEmptyOrNullString(String str){
        //String::isEmpty -> str.length() == 0
        return str == null || str.length() == 0 || str.chars().allMatch(Character::isWhitespace);
    }

    private static void testOptionalEmpty(){
        System.out.println("****************Optional Empty *************");
        Optional<String> OptionalEmpty = Optional.empty();
        //Optional<String> OptionalNull = Optional.of(null); //NOT ALLOWED -> NULL VALUE
        Optional<String> OptionalOfNullable = Optional.ofNullable(null); //Optional.empty
        Optional<String> OptionalSomeValue = Optional.of("someValue");
        System.out.println("OptionalOfNullable->"+OptionalOfNullable.toString());
        //Any operation on Optional.empty -> return Optional.empty
        System.out.println("OptionalEmpty.map(c->c.length())->"+OptionalEmpty.map(c->c.length())
                +
                ",OptionalEmpty.map(c->c.substring(2))->"+OptionalEmpty.map(c->c.substring(2)));
        System.out.println("OptionalSomeValue.map(c->c.length())->"+OptionalSomeValue.map(c->c.length())
                +
                ",OptionalSomeValue.map(c->c.substring(2))->"+OptionalSomeValue.map(c->c.substring(2)));
        System.out.println("OptionalEmpty.isPresent->"+OptionalEmpty.isPresent()+",OptionalSomeValue.isPresent->"+OptionalSomeValue.isPresent());
        OptionalEmpty.ifPresent(System.out::println);
        OptionalSomeValue.ifPresent(System.out::println);

        System.out.println("OptionalEmpty.map(c->c.toString())->"+OptionalEmpty.map(c->c.toString()));
        System.out.println("OptionalSomeValue.toString()->"+OptionalSomeValue.toString());
        System.out.println("OptionalEmpty.equals(Optional.empty())->"+OptionalEmpty.equals(Optional.empty()));
        System.out.println("OptionalSomeValue.equals(someValue)->"+OptionalSomeValue.equals("someValue")
                +",OptionalSomeValue.equals(Optional.of(someValue))->"+OptionalSomeValue.equals(Optional.of("someValue")));
        try{
            System.out.println("OptionalEmpty.get()->"+OptionalEmpty.get());
        }catch (NoSuchElementException e){System.out.println("EXCEPTION->NoSuchElementException: in OptionalEmpty.get()");}
        try{
            System.out.println("OptionalEmpty.orElseThrow()->"+OptionalEmpty.orElseThrow(()->new NoSuchElementException("empty found")));
        }catch (NoSuchElementException e){System.out.println("EXCEPTION->NoSuchElementException: in OptionalEmpty.orElseThrow()");}

    }
}
