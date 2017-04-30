package com.lviv.football.validators;

import com.lviv.football.ActionInfo;

import java.io.Serializable;

/**
 * Created by rudnitskih on 4/12/17.
 * Каждый класс который имплементирует этот интерфейс - содержит код ошибки и его написания,
 * по правильному нужно написать свою аннатоцию где через beanPostProcessor
 * мы будем их регистрировать и смотреть что каждый класс реализовал уникальный код.
 * Но я нашел времени и вдохновения этого сделать:) Если это реально важно - могу доделать.
 */
public interface ActionValidator extends Serializable {
    String validate(ActionInfo action);

    String getIssueCode();

    String getDescription();
}
