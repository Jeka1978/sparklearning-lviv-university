package org.kek5.Utils;

import org.apache.spark.sql.Row;
import org.kek5.Validators.Errors;
import org.springframework.context.annotation.ComponentScan;
import java.util.ArrayList;
import java.util.List;

@ComponentScan(lazyInit = true)
public class ErrorStatUtil {
    private static int length = Errors.class.getFields().length;

    public static List<String> getErrors(Row row) {
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if(row.getString(i) != null && !row.getString(i).isEmpty()) {
                errors.add(row.getString(i));
            }
        }

        return errors;
    }

}
