package com.lviv.football.savers;

import com.lviv.football.ActionInfo;
import scala.Tuple2;

import java.util.List;

/**
 * Created by rudnitskih on 4/15/17.
 */
public interface Saver {
    void saveValidationIssues(List<Tuple2<Integer, String>> amountIssuesByCode);

    void saveActions(List<ActionInfo> extendedData);
}
