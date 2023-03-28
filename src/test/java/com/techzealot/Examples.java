package com.techzealot;

import java.util.ArrayList;
import java.util.List;


public class Examples {
    public static void main(String[] args) {
        System.out.println(Integer.reverse(121));
        //泛型遵循协变逆变规则，泛型类遵循子类规则
        List<? extends Number> list = new ArrayList<Integer>();
    }
}
