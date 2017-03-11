package com.lviv.songs;

import lombok.SneakyThrows;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.StructField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.*;

/**
 * Created by Evegeny on 11/03/2017.
 */
@Service
public class LinkedInService {

    public static final String SALARY = "salary";
    public static final String AGE = "age";
    public static final String KEYWORDS = "keywords";
    public static final String KEYWORD = "keyword";
    public static final String AMOUNT = "amount";
    @Autowired
    private SQLContext sqlContext;
    public void printStuff() throws InterruptedException {
        DataFrame df = sqlContext.read().json("data/linkedIn/*.json");
        df.show();
        df.printSchema();
        StructField[] fields = df.schema().fields();
        for (StructField field : fields) {
            System.out.println(field.dataType());
        }
//        df = df.withColumn(SALARY, col(AGE).multiply(10).multiply(size(col(KEYWORDS))));
        df = df.withColumn(SALARY, callUDF(CalcSalary.class.getName(),col(AGE)));
        df.show();

        DataFrame keywordDF = df.withColumn(KEYWORD, explode(col(KEYWORDS))).select(KEYWORD);
        Row head = keywordDF.groupBy(col(KEYWORD)).agg(count(KEYWORD).as(AMOUNT)).orderBy(col(AMOUNT).desc()).head();
        String mostPopular = head.getAs(KEYWORD);
        System.out.println("mostPopular = " + mostPopular);

        df.where(col(SALARY).leq(1200).and(array_contains(col(KEYWORDS),mostPopular))).show();

    }

    @SneakyThrows
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(StartConfig.class).getBean(LinkedInService.class).printStuff();
    }
}














