package com.lviv.songs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Evegeny on 11/03/2017.
 */
@Component
public class UserConfig implements Serializable{
    public List<String> garbage;

    @Value("${garbage}")
    private void setGarbage(String[] garbage) {
        this.garbage = Arrays.asList(garbage);
    }
}
