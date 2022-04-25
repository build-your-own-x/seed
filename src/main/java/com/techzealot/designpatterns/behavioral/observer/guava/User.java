package com.techzealot.designpatterns.behavioral.observer.guava;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private int age;
}
