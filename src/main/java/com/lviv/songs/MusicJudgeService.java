package com.lviv.songs;

import java.util.List;

/**
 * Created by Evegeny on 11/03/2017.
 */
public interface MusicJudgeService {
    List<String> topX(String artistName, int x);
}
