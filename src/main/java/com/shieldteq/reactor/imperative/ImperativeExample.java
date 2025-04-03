package com.shieldteq.reactor.imperative;

import java.util.List;

public class ImperativeExample {
    public static void main(String[] args) {
        List<String> nameList = List.of("alex", "ben", "chloe", "adam");
        System.out.println(namesGreaterThanSize(nameList, 3));
        System.out.println(namesGreaterThanSize(nameList, 2));
    }

    private static List<String> namesGreaterThanSize(List<String> nameList, int size) {
        return nameList.stream().filter(name -> name.length() > size).distinct().sorted().map(String::toUpperCase).toList();
    }
}
