package com.techzealot.designpatterns.behavioral.mediator.generalized;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 部门和员工间存在多对多关系，采用相互持有集合的话，会在双方发生变更时难以处理
 * 可采用广义中介者模式优化，简化掉Mediator和Colleague接口
 */
@Data
@AllArgsConstructor
public class Employee {
    private String id;

    private String name;

    public void delete() {
        //1.由中介者去除与部门相关的部门与人员
        DepEmpMediator.getInstance().deleteEmployee(id);
        //2.真正执行清理逻辑，如软删除
        System.out.println("soft delete employee :" + id);
    }

}
