package com.lviv.spark.infrastructure;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Anatoliy on 12.04.2017.
 */
@Component
public class FromPropertyFileBPP implements BeanPostProcessor {
    @Override
    @SneakyThrows
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(FromPropertyFile.class)) {
                FromPropertyFile ann = method.getAnnotation(FromPropertyFile.class);
                String fileName = ann.fileName();
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                try (InputStream stream = loader.getResourceAsStream(fileName)) {
                    List<String> doc =
                            new BufferedReader(new InputStreamReader(stream,
                                    StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
                    method.invoke(bean, doc);
                } catch (IOException ex) {

                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        return bean;
    }
}
