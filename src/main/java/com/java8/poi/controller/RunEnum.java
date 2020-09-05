package com.java8.poi.controller;

public class RunEnum {
    public static void main(String[] args) {
        TestEnum t = new TestEnum();
        t.setStatus(TestEnum.STATUS.OK);

        System.out.println(t);
    }
}
