package com.techzealot.designpatterns.behavioral.mediator.generalized;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Department {

    private String id;

    private String name;

    public void delete() {
        //1.由中介者去除与部门相关的部门与人员
        DepEmpMediator.getInstance().deleteDepartment(id);
        //2.真正执行清理逻辑，如软删除
        System.out.println("soft delete department :" + id);
    }
}
