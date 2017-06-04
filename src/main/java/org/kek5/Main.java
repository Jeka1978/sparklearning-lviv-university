package org.kek5;

import org.kek5.Config.StartConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by kek5 on 4/17/17.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(StartConfig.class);
        context.getBean(FootballService.class).doWork();
    }
}
