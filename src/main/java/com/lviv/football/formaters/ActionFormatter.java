package com.lviv.football.formaters;

import com.lviv.football.ActionInfo;

import java.io.Serializable;

/**
 * Created by rudnitskih on 4/15/17.
 */
public interface ActionFormatter extends Serializable {
    ActionInfo addAdditionalData(ActionInfo action);
}
