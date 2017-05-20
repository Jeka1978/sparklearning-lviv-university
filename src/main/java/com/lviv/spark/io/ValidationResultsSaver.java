package com.lviv.spark.io;

import com.lviv.spark.validation.ValidationResults;

/**
 * Created by Anatoliy on 12.04.2017.
 */
public interface ValidationResultsSaver {
    void saveValidation(ValidationResults result, String file);
}
