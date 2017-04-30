package com.lviv.football.enrichers;

import com.lviv.football.ActionInfo;

import java.io.Serializable;

/**
 * Created by rudnitskih on 4/15/17.
 */
public interface DataEnricher extends Serializable {
    ActionInfo addAdditionalData(ActionInfo action);
}
