import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kek5.Config.Columns.ErrorColumns;
import org.kek5.Config.StartConfig;
import org.kek5.Utils.ErrorCollector;
import org.kek5.Utils.ErrorStatCollector;
import org.kek5.Utils.FromStrings2DataFrame;
import org.kek5.Validators.Errors;
import org.kek5.Validators.ValidatorAggregator;
import org.kek5.Validators.ValidatorAggregatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kek5 on 6/1/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StartConfig.class, TestDataFrame.class})
public class ErrorCollectorTest {
    @Autowired
    private ErrorStatCollector errorStatCollector;
    @Autowired
    private ErrorCollector errorCollector;
    @Autowired
    private ValidatorAggregator validatorAggregator;

    private DataFrame errorStatistics;



    @Test
    public void participantsErrorCountTest() {
        DataFrame participantsError = errorStatistics.filter(errorStatistics.col(ErrorColumns.ERROR_NAME)
        .equalTo(Errors.PARTICIPANT_ERROR_COLUMN));

        String participantsErrorCount = participantsError.head().getAs("Count"); // Error player
        Assert.assertEquals("2", participantsErrorCount);
    }
    @Test
    public void playerErrorCountTest() {
        DataFrame playerError = errorStatistics.filter(errorStatistics.col(ErrorColumns.ERROR_NAME)
                .equalTo(Errors.PLAYER_ERROR_COLUMN));

        String playerErrorCount = playerError.head().getAs("Count"); // Error player
        Assert.assertEquals("2", playerErrorCount);
    }
    @Test
    public void timeErrorCountTest() {
        DataFrame playerError = errorStatistics.filter(errorStatistics.col(ErrorColumns.ERROR_NAME)
                .equalTo(Errors.TIME_ERROR_COLUMN));

        String playerErrorCount = playerError.head().getAs("Count"); // Error player
        Assert.assertEquals("1", playerErrorCount);
    }


    @PostConstruct
    private void init() {
        DataFrame df = TestDataFrame.df;

        df = validatorAggregator.validate(df);
        DataFrame validDF = errorCollector.collect(df);
        DataFrame errorDF = df.except(validDF);
        this.errorStatistics = errorStatCollector.generateStatistics(errorDF);
    }
}
