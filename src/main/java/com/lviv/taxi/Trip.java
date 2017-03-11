package com.lviv.taxi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Evegeny on 11/03/2017.
 */
@Data
@AllArgsConstructor
public class Trip {
    private String id;
    private String city;
    private int km;

}
