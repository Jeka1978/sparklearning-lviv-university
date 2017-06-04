package org.kek5.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kek5 on 4/18/17.
 */
public class FieldMapperUtil {
    public static Map<String, String> line2Map(String line) {
        Map<String, String> map = new HashMap();
        String[] pieces = line.split(";");
        for (String piece : pieces) {
            String[] key_value = piece.split("=");
            try {
                map.put(key_value[0], key_value[1]); // key - value
            } catch (ArrayIndexOutOfBoundsException e) {
                map.put(key_value[0], null);
            }
        }

        return map;
    }
}
