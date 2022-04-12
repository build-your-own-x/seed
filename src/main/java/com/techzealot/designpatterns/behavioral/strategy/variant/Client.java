package com.techzealot.designpatterns.behavioral.strategy.variant;

public class Client {
    public static void main(String[] args) {
        Context ctx=new Context();
        ctx.doSome("A",new Params("A",1,true));
        ctx.doSome("B",new Params("B",2,false));
        ctx.doSome("C",new Params("C",3,true));
    }
}
