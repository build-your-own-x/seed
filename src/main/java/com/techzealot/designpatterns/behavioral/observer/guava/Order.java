package com.techzealot.designpatterns.behavioral.observer.guava;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private long id;
    private String NO;
    private int price;
}
