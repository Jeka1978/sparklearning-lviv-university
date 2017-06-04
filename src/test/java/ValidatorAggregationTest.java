import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by kek5 on 5/7/17.
 */
import org.kek5.Config.StartConfig;
import org.kek5.Utils.DataFrameCreator;
import org.kek5.Utils.FromRows2DataFrame;
import org.kek5.Utils.FromStrings2DataFrame;
import org.kek5.Validators.ValidatorAggregator;
import org.kek5.Validators.ValidatorAggregatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import scala.collection.immutable.Map;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StartConfig.class)
public class ValidatorAggregationTest {
    @Autowired
    private JavaSparkContext sc;
    @Autowired
    private FromStrings2DataFrame fromStrings2DataFrame;
    @Autowired
    private ValidatorAggregatorImpl validator;

    @Test
    public void validatePlayer() {
        List<String> list = Arrays.asList(
                "code=2;from=Vasia Pypkin;to=Sami KhediraApri;eventTime=2:12;stadion=they;startTime=21:39;");
        JavaRDD<String> rdd = sc.parallelize(list);
        DataFrame df = fromStrings2DataFrame.create(rdd);
        df = validator.validate(df);
        Row head = df.head();
        String playerError = head.getAs("ErrorPlayer");

        Assert.assertEquals("error", playerError);

    }
    @Test
    public void validateTime() {
        List<String> list = Arrays.asList(
                "code=2;from=Vasia Pypkin;to=Sami KhediraApri;eventTime=122:12;stadion=they;startTime=21:39;");
        JavaRDD<String> rdd = sc.parallelize(list);
        DataFrame df = fromStrings2DataFrame.create(rdd);
        df = validator.validate(df);
        Row head = df.head();
        String timeError = head.getAs("ErrorTime");

        Assert.assertEquals("error", timeError);
    }
    @Test
    public void validateParticipant() {
        List<String> list = Arrays.asList(
                "code=7;from=Vasia Pypkin;to=Sami KhediraApri;eventTime=2:12;stadion=they;startTime=121:39;");
        JavaRDD<String> rdd = sc.parallelize(list);
        DataFrame df = fromStrings2DataFrame.create(rdd);
        df = validator.validate(df);
        Row head = df.head();
        String participantError = head.getAs("ErrorParticipant");

        Assert.assertEquals("error", participantError);
    }
}
