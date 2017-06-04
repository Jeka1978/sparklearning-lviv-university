package org.kek5.UDFs.Filters;

import org.apache.spark.sql.api.java.UDF3;
import org.kek5.Validators.Errors;


@org.kek5.Annotations.UDF3
public class ParticipantsFilter implements UDF3<String, String, String, String> {

    @Override
    public String call(String code, String from, String to) throws Exception {
        int intCode = Integer.parseInt(code);
        if(intCode == 1 || intCode == 2) {
            if(from != null || to == null) {
                return Errors.PARTICIPANT_ERROR_COLUMN;
            }
        } else if (intCode > 4 && intCode < 10) {
            if(from == null || to != null) {
                return Errors.PARTICIPANT_ERROR_COLUMN;
            }
        } else {
            if(from == null || to == null) {
                return Errors.PARTICIPANT_ERROR_COLUMN;
            }
        }
        return "";
    }
}
