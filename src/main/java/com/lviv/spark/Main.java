package com.lviv.spark;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Anatoliy on 05.04.2017.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        FootballService service = context.getBean(FootballService.class);
        service.processFootballDataset();
    }
}
