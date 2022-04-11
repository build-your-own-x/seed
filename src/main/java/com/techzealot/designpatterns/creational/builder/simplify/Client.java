package com.techzealot.designpatterns.creational.builder.simplify;

public class Client {

    public static void main(String[] args) {
        ManyFieldsObject manyFieldsObject = ManyFieldsObject.builder().s("s").i(1).b(true).d(1.0).build();
        System.out.println(manyFieldsObject);
        LombokManyFieldsObject lombokManyFieldsObject = LombokManyFieldsObject.builder().s("s").i(1).b(true).d(1.0).build();
        System.out.println(lombokManyFieldsObject);

    }
}
