package com.java8.poi.controller;

public class TestEnum {

    public enum STATUS {OK,FAIL}

    private String message;
    private STATUS status;

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TestEnum{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
