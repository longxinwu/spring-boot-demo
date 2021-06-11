package com.longxinwu.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties 告诉SpringBoot将本类中的所有属性和配置文件中相关配置进行绑定
 */
@Data
@Component
//@ConfigurationProperties(prefix = "person")
public class Person {
    /**
     * person:
     *   name: zhangsan
     *   age: 13
     *   maps: {k1: v1,k2: v2}
     *   lists:
     *     - lisi
     *     - wangwu
     */
    //@Value("${person.name}")
    private String name;
    private int age;
    private Map<String, Object> map;
    private List<Object> lists;
    private Dog dog;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", map=" + map +
                ", lists=" + lists +
                ", dog=" + dog +
                '}';
    }
}
