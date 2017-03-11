package com.lviv.songs.infra;

import lombok.SneakyThrows;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Evegeny on 11/03/2017.
 */
@Component
public class UDFRegistratorBPP implements BeanPostProcessor {
    @Autowired
    private SQLContext sqlContext;

    @Override
    @SneakyThrows
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(UDF.class)) {
//            Field field = bean.getClass().getGenericInterfaces()[0].getClass().getDeclaredField("actualTypeArguments");
//            field.setAccessible(true);
            sqlContext.udf().register(bean.getClass().getName(), (UDF1) bean, DataTypes.LongType);

        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
