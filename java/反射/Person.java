package com.pf.反射;

/**
 * @author zhaopf
 * @version 1.0
 * @QQ: 1308108803
 * @date 2018年01月04日
 */
public class Person {

    private String name;

    private int age;

    private Person p;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person getP() {
        return p;
    }

    public void setP(Person p) {
        this.p = p;
    }

    public static String printInfo() {
        System.out.println("这里是静态方法");
        return "访问了静态方法";
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", p=" + p +
                '}';
    }
}