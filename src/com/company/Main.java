package com.company;

public class Main {

    public static void main(String[] args) {
        String URL = "http:?query#fragment";
        String[] array = new Parser().parse(URL);
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                System.out.println(array[i]);
            }
        } else {
            System.out.println("null");
        }
    }
}
