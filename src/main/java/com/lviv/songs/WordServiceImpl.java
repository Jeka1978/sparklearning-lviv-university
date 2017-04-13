package com.lviv.songs;

import com.lviv.songs.infra.AutowiredBroadcast;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.List;

/**
 * Created by Evegeny on 11/03/2017.
 */
@Service
public class WordServiceImpl implements WordService {
    @AutowiredBroadcast
    private Broadcast<UserConfig> userConfig;

    @Override
    public List<String> topX(JavaRDD<String> lines, int x) {
        return lines.map(String::toLowerCase)
                .flatMap(WordsUtil::getWords)
                .filter(word->!this.userConfig.value().garbage.contains(word))
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey(Integer::sum)
                .mapToPair(Tuple2::swap)
                .sortByKey(false)
                .map(Tuple2::_2).take(x);
    }
}
