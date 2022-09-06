package com.example.demo.data.structure.entity;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/9/3
 * @package: com.example.demo.data.structure.entity
 */
public class Student implements Comparable<Student>{

    private int age;

    private String name;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        if(this.age>o.age){
            return 1;
        }
        if(this.age==o.age){
            return 0;
        }
        if(this.age<o.age){
            return -1;
        }
        return 0;
    }
}
