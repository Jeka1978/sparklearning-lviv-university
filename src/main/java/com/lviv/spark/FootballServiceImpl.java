package com.lviv.spark;

import com.lviv.spark.io.CsvReader;
import com.lviv.spark.io.CsvToClassReader;
import com.lviv.spark.io.ValidationResultsSaver;
import com.lviv.spark.validation.ValidationResults;
import com.lviv.spark.validation.ValidationService;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

/**
 * Created by Anatoliy on 05.04.2017.
 */
@Service
public class FootballServiceImpl implements FootballService {

    @Autowired
    JavaSparkContext sparkContext;

    @Autowired
    ValidationService validationService;

    @Autowired
    CsvToClassReader csvReader;

    @Autowired
    ValidationResultsSaver validationResultsSaver;

    @Value("${inputFile}")
    String inputFile;

    @Value("${outputFile}")
    String outputFile;

    @Value("${validationResultFile}")
    String validationResultFile;

    @Override
    public void processFootballDataset() {
        JavaRDD<FootballAction> rdd = csvReader.mapToRows(sparkContext.textFile(inputFile));

        Tuple2<JavaRDD<Row>, ValidationResults> validationResults = validationService.validateAndFilter(rdd);
        validationResultsSaver.saveValidation(validationResults._2(), validationResultFile);
    }
}
