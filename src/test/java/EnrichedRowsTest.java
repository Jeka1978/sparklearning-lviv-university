import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kek5.Config.StartConfig;
import org.kek5.Enrichers.RowEnrichers.RowEnricherAggregator;
import org.kek5.Enrichers.RowEnrichers.TempColumns;
import org.kek5.Utils.ErrorCollector;
import org.kek5.Validators.ValidatorAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

/**
 * Created by kek5 on 6/4/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StartConfig.class, TestDataFrame.class})
public class EnrichedRowsTest {
    private DataFrame enrichedDF;

    @Autowired
    private ErrorCollector errorCollector;
    @Autowired
    private ValidatorAggregator validatorAggregator;
    @Autowired
    private RowEnricherAggregator rowEnricher;

    @Test
    public void enrichedRowsTest() {
//        code=3;from=Jonathan Tah;to=Oleg Shatov;eventTime=2:12;stadion=they;startTime=11:39

        Row test = this.enrichedDF.head();
        String code = test.getAs(TempColumns.newCode.split("_")[1]);
        String from = test.getAs(TempColumns.newFrom.split("_")[1]);
        String to = test.getAs(TempColumns.newTo.split("_")[1]);
        String description = test.getAs(TempColumns.newDiscription.split("_")[1]);

        Assert.assertEquals("Jonathan Tah", to);
        Assert.assertEquals("4", code);
        Assert.assertEquals("Oleg Shatov", from);
        Assert.assertEquals("pass received", description);
    }

    @PostConstruct
    private void init() {
        DataFrame df = TestDataFrame.df;

        df = validatorAggregator.validate(df);
        DataFrame validDF = errorCollector.collect(df);
        this.enrichedDF = rowEnricher.enrich(validDF);
    }
}
