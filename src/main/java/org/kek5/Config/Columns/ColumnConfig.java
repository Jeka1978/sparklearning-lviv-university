package org.kek5.Config.Columns;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kek5 on 4/24/17.
 */
public interface ColumnConfig extends Serializable{
    List<String> getColumns();
}
