package org.kek5.Annotations;

import org.kek5.Config.Columns.ColumnTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by kek5 on 5/9/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
@Qualifier
@Autowired
public @interface ColumnQualifier {
    ColumnTypes value();
}
