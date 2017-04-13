package com.lviv.spark.validation;

import com.lviv.songs.infra.AutowiredBroadcast;
import com.lviv.spark.properties.ColumnsProperties;
import com.lviv.spark.properties.FootballTeamsProperties;
import com.lviv.spark.utils.RowUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Anatoliy on 11.04.2017.
 */
@Component
public class PlayerNameRowValidationRule implements Serializable, ValidationRule {
    @AutowiredBroadcast
    private Broadcast<FootballTeamsProperties> teamsProperties;

    @AutowiredBroadcast
    private Broadcast<ColumnsProperties> columnsProperties;

    @Override
    public JavaRDD<Row> validate(JavaRDD<Row> rdd) {

        return rdd.map(row -> {
            ColumnsProperties cp = columnsProperties.value();
            FootballTeamsProperties tp = teamsProperties.value();
            int invalid = 0;

            String player1 = row.getString(cp.getColumnIndex("from"));
            String player2 = row.getString(cp.getColumnIndex("to"));
            if(!tp.ContainsPlayer(player1) || !tp.ContainsPlayer(player2)) {
                invalid = 1;
            }

            return RowUtils.AddValueToRow(row, invalid);
        });
    }

    @Override
    public String columnName() {
        return "PlayerNameInvalid";
    }

    @Override
    public String[] requiredColumns() {
        return new String[]{"from", "to"};
    }
}
