import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.junit.runner.RunWith;
import org.kek5.Config.Columns.ColumnConfig;
import org.kek5.Utils.FromStrings2DataFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kek5 on 6/4/17.
 */
@Component
@ComponentScan("org.kek5.Config")
public class TestDataFrame {
    @Autowired
    private JavaSparkContext sc;
    @Autowired
    private FromStrings2DataFrame fromStrings2DataFrame;


    public static DataFrame df;


    @PostConstruct
    private void initDF() {
        List<String> list = Arrays.asList(
                "code=3;from=Vasia Pypkin;to=Sami KhediraApri;eventTime=112:12;stadion=they;startTime=12:39;", //error time, player
                "code=1;from=Jonathan Tah;to=Oleg Shatov;eventTime=23:12;stadion=they;startTime=12:39;", // error participant
                "code=9;from=Jonathan Tah;to=Sveta Loboda;eventTime=2:12;stadion=they;startTime=11:39;", // error player, participants
                "code=3;from=Jonathan Tah;to=Oleg Shatov;eventTime=2:12;stadion=they;startTime=11:39;"); // error participants, player

        JavaRDD<String> rdd = sc.parallelize(list);
        TestDataFrame.df = fromStrings2DataFrame.create(rdd);
    }

}
