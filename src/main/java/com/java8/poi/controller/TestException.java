package com.java8.poi.controller;

public class TestException {
    public static void main(String[] args) {
        TestException t = new TestException();
        try{
            t.e1();
        }catch (MYException e){
            System.out.println("catched......->"+e.getMessage());
        }catch (Exception e){
            System.out.println("catched generic.....->"+e.getMessage());
        }
    }

    public void e1() throws MYException {
        System.out.println("start - e1");
        e2();
        System.out.println("end - e1");

    }
    public void e2() throws MYException {
        System.out.println("start - e2");
        if(true)
        throw new MYException("something error happened");
        System.out.println("end - e2");
    }
}

class MYException extends Exception {

    public MYException(String message) {
        super(message);
    }

}
