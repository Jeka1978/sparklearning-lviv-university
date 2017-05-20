package com.lviv.spark;

import com.lviv.spark.enrichment.DataEnrichmentService;
import com.lviv.spark.io.ActionToCsvWriter;
import com.lviv.spark.io.CsvToActionReader;
import com.lviv.spark.io.ValidationResultsSaver;
import com.lviv.spark.validation.ValidationResults;
import com.lviv.spark.validation.action.ActionValidationService;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Anatoliy on 05.04.2017.
 */
@Service
public class FootballServiceImpl implements FootballService {

    @Autowired
    JavaSparkContext sparkContext;

    @Autowired
    ActionValidationService validationService;

    @Autowired
    CsvToActionReader csvReader;

    @Autowired
    ActionToCsvWriter csvWriter;

    @Autowired
    ValidationResultsSaver validationResultsSaver;
    
    @Autowired
    DataEnrichmentService enrichmentService;

    @Value("${inputFile}")
    String inputFile;

    @Value("${outputFile}")
    String outputFile;

    @Value("${validationResultFile}")
    String validationResultFile;

    @Override
    public void processFootballDataset() {
        // input
        JavaRDD<FootballAction> rdd = csvReader.mapToActions(sparkContext.textFile(inputFile));

        // validation
        Tuple2<JavaRDD<FootballAction>, ValidationResults> validationResults = validationService.validateAndFilter(rdd);
        validationResultsSaver.saveValidation(validationResults._2(), validationResultFile);
        JavaRDD<FootballAction> filtered = validationResults._1();
        
        // enrichment
        JavaRDD<FootballAction> enriched = enrichmentService.enrichData(filtered);
        
        // write to file
        csvWriter.write(enriched, outputFile);
    }
}
