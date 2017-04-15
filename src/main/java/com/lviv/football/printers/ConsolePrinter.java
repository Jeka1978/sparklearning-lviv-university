package com.lviv.football.printers;

import com.lviv.football.ActionInfo;
import com.lviv.football.validators.ActionValidator;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.List;
import java.util.Objects;

/**
 * Created by rudnitskih on 4/14/17.
 */
@Service
public class ConsolePrinter implements Printer {
    @Autowired
    List<ActionValidator> validators;

    @Override
    public void printActions(JavaRDD<ActionInfo> actionsRDD,
                             int amountFirstLines,
                             String[] columnNames,
                             String tableDescription) {
        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow(columnNames);

        actionsRDD
            .take(amountFirstLines)
            .forEach(action -> {
                String[] columnValues = new String[columnNames.length];

                for (int i = 0; i < columnNames.length; i++) {
                    columnValues[i] = action.getProperty(columnNames[i]);
                }

                at.addRule();
                at.addRow(columnValues);
            });

        at.addRule();

        at.getContext().setWidth(120);

        String rend = at.render();

        if (tableDescription != null) {
            System.out.println(tableDescription);
        }

        System.out.println(rend);
    }

    @Override
    public void printValidationIssues(List<Tuple2<Integer, String>> amountIssuesByCode) {
        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow(
            "Issue Code",
            "Amount",
            "Issue Description"
        );

        amountIssuesByCode
            .forEach(issue -> {
                String code = issue._2();
                Integer amount = issue._1();
                String description = "";

                for (ActionValidator validator : validators) {
                    if (Objects.equals(validator.getIssueCode(), code)) {
                        description = validator.getDescription();
                        break;
                    }
                }

                at.addRule();

                at.addRow(
                    code,
                    amount,
                    description
                );
            });

        at.addRule();

        at.setTextAlignment(TextAlignment.LEFT);

        String rend = at.render();

        System.out.println("\nValidation issues:");
        System.out.println(rend);
    }
}
