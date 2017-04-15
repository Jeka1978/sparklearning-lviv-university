package com.lviv.football.savers;

import com.lviv.football.ActionInfo;
import com.lviv.football.validators.ActionValidator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

import static com.lviv.football.constants.Paths.resultsDirectory;

/**
 * Created by rudnitskih on 4/14/17.
 */
@Service
public class FileSaver implements Saver {
    @Autowired
    List<ActionValidator> validators;

    @Override
    @SneakyThrows
    public void saveValidationIssues(List<Tuple2<Integer, String>> amountIssuesByCode) {
        PrintWriter writer = new PrintWriter(resultsDirectory + "validation-issues.txt", "UTF-8");

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

                writer.println("code=" + code + ";amount=" + amount + ";description=" + description);
            });

        writer.close();
    }

    @Override
    @SneakyThrows
    public void saveActions(List<ActionInfo> extendedData) {
        PrintWriter writer = new PrintWriter(resultsDirectory + "extended-actions.txt", "UTF-8");

        extendedData
            .forEach(action -> {
                writer.println(action.getProperties());
            });

        writer.close();
    }
}
