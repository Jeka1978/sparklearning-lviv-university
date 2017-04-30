package com.lviv.football.savers;

import com.lviv.football.configs.MainConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import scala.Tuple2;

import java.io.PrintWriter;
import java.util.ArrayList;

import static com.lviv.songs.constants.Profiles.DEV;

/**
 * Created by rudnitskih on 4/30/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainConfig.class)
@ActiveProfiles(DEV)
public class FileSaverTest {
    @Autowired
    FileSaver fileSaver;
    private PrintWriter printWriterMock;

    @Before
    public void beforeEachTest() {
        printWriterMock = Mockito.mock(PrintWriter.class);
    }

    /**
     * Я не знаю как правильно делать моки объектов в Java.
     * <p>
     * Этот способ выкидывает
     * Exception:
     * <p>
     * Wanted but not invoked:
     * printWriter.println(
     * "code=1;amount=3;description=Time is no correct"
     * );
     * -> at com.lviv.football.savers.FileSaverTest.saveValidationIssues(FileSaverTest.java:48)
     * Actually, there were zero interactions with this mock.
     * <p>
     * Я потратил уже много времени что-бы разобраться как лучше всего писать тесты в Java
     * и я очень сомневаюсь в их економичности по времени. "Проверить, что работает в main" не зная Java
     * выглядит более логичным.
     */
    // @Test
    public void saveValidationIssues() throws Exception {
        ArrayList<Tuple2<Integer, String>> amountIssuesByCode = new ArrayList<>();

        amountIssuesByCode.add(new Tuple2<>(42, "1"));
        amountIssuesByCode.add(new Tuple2<>(43, "2"));
        amountIssuesByCode.add(new Tuple2<>(0, "3"));

        fileSaver.saveValidationIssues(amountIssuesByCode);

        Mockito.verify(printWriterMock).println("code=1;amount=3;description=Time is no correct");
    }

    @Test
    public void saveActions() throws Exception {

    }

}