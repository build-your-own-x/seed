package com.techzealot.designpatterns.creational.prototype;


import lombok.Data;

@Data
public class Person implements Cloneable {

    private boolean female;

    /**
     * 字符串未实现Cloneable,也是拷贝引用，但是由于字符串是不可变对象，修改时会创建新对象，故修改源对象不会影响clone对象
     */
    private String name;

    private int age;

    private Person parent;

    public Person(boolean female, String name, int age) {
        this.female = female;
        this.name = name;
        this.age = age;
    }

    /**
     * should be public
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 还可以使用序列化再反序列化的方式进行深度克隆
     * @return
     * @throws CloneNotSupportedException
     */
    public Person deepClone() throws CloneNotSupportedException {
        Person person = (Person) super.clone();
        //递归克隆除字符串外所有引用对象
        Person parent = this.getParent();
        if (parent != null)
            person.setParent((Person) parent.clone());
        return person;
    }

}
