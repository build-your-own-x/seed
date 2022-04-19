package com.techzealot.designpatterns.structural.adapter;

/**
 * 实现要适配的接口
 */
public class ApiV2Adapter implements ApiV2 {

    //持有被适配的对象
    private final ApiV1 apiV1;


    public ApiV2Adapter(ApiV1 apiV1) {
        this.apiV1 = apiV1;
    }

    @Override
    public String doV2(String args) {
        //使用被适配对象兼容新功能
        return apiV1.doV1(args);
    }
}
