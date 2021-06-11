package com.longxinwu.bean;

import lombok.Data;

@Data
public class Dog {
    /**
     * dog:
     *    name: 小黑
     *    age: 2
     */
    private String name;
    private int age;

    public Dog() {
    }

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
