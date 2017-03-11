package com.lviv.songs;

import com.lviv.songs.constants.Profiles;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * Created by Evegeny on 11/03/2017.
 */
@Service
public class MusicJudgeServiceImpl implements MusicJudgeService {

    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private WordService wordService;

    @Override
    public List<String> topX(String artistName, int x) {
        JavaRDD<String> rdd = sc.textFile("data/songs/" + artistName + "/*.txt");
        return wordService.topX(rdd,x);
    }
}
