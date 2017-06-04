package org.kek5.BPPs;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.apache.spark.sql.api.java.UDF1;

/**
 * Created by kek5 on 4/19/17.
 */
@Component
public class UDF1BPP implements BeanPostProcessor {
    @Autowired
    SQLContext sqlContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(org.kek5.Annotations.UDF1.class)) {
            sqlContext.udf().register(bean.getClass().getName(), (UDF1<?, ?>) bean, DataTypes.StringType);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
