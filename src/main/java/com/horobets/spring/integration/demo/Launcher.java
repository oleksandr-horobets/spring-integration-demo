package com.horobets.spring.integration.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher {
    public static void main(String... args) {
        new ClassPathXmlApplicationContext("classpath:/common.xml");
    }
}
