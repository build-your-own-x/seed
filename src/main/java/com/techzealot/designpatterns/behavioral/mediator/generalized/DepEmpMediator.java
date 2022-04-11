package com.techzealot.designpatterns.behavioral.mediator.generalized;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

public class DepEmpMediator {

    private DepEmpMediator() {
        initData();
    }

    private static final DepEmpMediator instance = new DepEmpMediator();

    public static DepEmpMediator getInstance() {
        return instance;
    }

    public void deleteDepartment(String id) {
        relations.removeIf(e -> id.equals(e.getDepId()));
    }

    public void deleteEmployee(String id) {
        relations.removeIf(e -> id.equals(e.getEmpId()));
    }

    @Getter
    private final Collection<DepEmp> relations = new ArrayList<>();

    private void initData() {
        relations.add(new DepEmp(1, "d1", "e1"));
        relations.add(new DepEmp(2, "d1", "e2"));
        relations.add(new DepEmp(3, "d1", "e3"));
        relations.add(new DepEmp(4, "d2", "e4"));
        relations.add(new DepEmp(5, "d2", "e2"));
        relations.add(new DepEmp(6, "d3", "e3"));
        relations.add(new DepEmp(7, "d3", "e1"));
    }
}
