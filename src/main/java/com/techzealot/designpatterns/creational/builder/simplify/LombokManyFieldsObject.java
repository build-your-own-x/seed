package com.techzealot.designpatterns.creational.builder.simplify;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LombokManyFieldsObject {
    private String s;
    private int i;
    private boolean b;
    private double d;
}
