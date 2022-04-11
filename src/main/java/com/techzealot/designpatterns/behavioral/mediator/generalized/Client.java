package com.techzealot.designpatterns.behavioral.mediator.generalized;

public class Client {

    public static void main(String[] args) {
        Department d1 = new Department("d1", "D1");
        Department d2 = new Department("d2", "D2");
        Department d3 = new Department("d3", "D3");
        Employee e1 = new Employee("e1", "E1");
        Employee e2 = new Employee("e2", "E2");
        Employee e3 = new Employee("e3", "E3");
        Employee e4 = new Employee("e4", "E4");
        d2.delete();
        System.out.println("after delete d2:");
        DepEmpMediator.getInstance().getRelations().forEach(System.out::println);
        e1.delete();
        System.out.println("after delete e1:");
        DepEmpMediator.getInstance().getRelations().forEach(System.out::println);
    }
}
