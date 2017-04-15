package com.lviv.football;

import com.lviv.football.configs.MainConfig;
import com.lviv.football.constants.Columns;
import com.lviv.football.printers.Printer;
import com.lviv.football.savers.Saver;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import scala.Tuple2;

import java.util.List;

import static com.lviv.football.constants.Paths.resultsDirectory;

/**
 * Created by rudnitskih on 4/11/17.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

        JavaSparkContext sc = context.getBean(JavaSparkContext.class);
        DataProcessor dataProcessor = context.getBean(DataProcessor.class);
        Printer printer = context.getBean(Printer.class);
        Saver saver = context.getBean(Saver.class);

        JavaRDD<String> rawData = sc.textFile("data/rawData.txt");

        JavaRDD<ActionInfo> actionsWithValidationIssuesRDD = dataProcessor.getActionsWithValidationIssues(rawData);

        actionsWithValidationIssuesRDD.persist(StorageLevel.MEMORY_AND_DISK());

        printer.printActions(
            actionsWithValidationIssuesRDD,
            10,
            new String[]{
                Columns.code,
                Columns.from,
                Columns.eventTime,
                Columns.to,
                Columns.stadion,
                Columns.startTime,
                Columns.validationIssues
            },
            "\nInitial data with validation issue:"
        );

        List<Tuple2<Integer, String>> amountIssuesByCode = dataProcessor
            .getAmountIssuesByCode(actionsWithValidationIssuesRDD);

        printer.printValidationIssues(amountIssuesByCode);
        saver.saveValidationIssues(amountIssuesByCode);

        JavaRDD<ActionInfo> extendedData = dataProcessor.getExtendedData(actionsWithValidationIssuesRDD);

        extendedData.persist(StorageLevel.MEMORY_AND_DISK());

        printer.printActions(
            extendedData,
            10,
            new String[]{
                Columns.code,
                Columns.actionDescription,
                Columns.from,
                Columns.team,
                Columns.eventTime,
                Columns.period
            },
            "\nExtended data:"
        );

        saver.saveActions(extendedData.collect());

        System.out.println("\n=== You can found all results in `" + resultsDirectory + "` directory. ===");
    }
}
