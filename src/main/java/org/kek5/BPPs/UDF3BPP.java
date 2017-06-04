package org.kek5.BPPs;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.api.java.UDF3;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by kek5 on 4/20/17.
 */
@Component
public class UDF3BPP implements BeanPostProcessor {
    @Autowired
    SQLContext sqlContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(org.kek5.Annotations.UDF3.class)) {
            Method[] methods = bean.getClass().getMethods();
            if(methods[0].getReturnType() == String.class) {
                sqlContext.udf().register(bean.getClass().getName(), (UDF3<?, ?, ?, ?>) bean, DataTypes.StringType);
            } else {
                sqlContext.udf().register(bean.getClass().getName(), (UDF3<?, ?, ?, ?>) bean, DataTypes.BooleanType);
            }

        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
