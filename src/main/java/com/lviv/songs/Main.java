package com.lviv.songs;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Created by Evegeny on 11/03/2017.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(StartConfig.class);
        MusicJudgeService judgeService = context.getBean(MusicJudgeService.class);
        List<String> topX = judgeService.topX("beatles", 3);
        System.out.println("topX = " + topX);
    }
}
