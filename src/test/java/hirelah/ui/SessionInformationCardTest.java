package hirelah.ui;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.testutil.TypicalInterviewee;

import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class SessionInformationCardTest {
    private static final String SESSION_NAME_ID = "#sessionName";
    private static final String INTERVIEWS_ID = "#interviews";
    private static final String FINALISE_ID = "#finaliseStatus";
    private static final boolean NOT_FINALISED = false;


    @AfterEach
    public void pause(FxRobot robot) {
        robot.sleep(500);
    }

    @Stop
    void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Initialise SessionInformationCard test with a stage.
     *
     * @param stage Stage used to test.
     */
    @Start
    public void start(Stage stage) throws IllegalValueException {
        Scene scene = new Scene(new SessionInformationCard(Paths.get("data\\Test"),
                TypicalInterviewee.getObservableIntervieweeList(), false).getRoot());
        scene.getStylesheets().add("/view/DarkTheme.css");
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void sessionNameIsDisplayedCorrectly() {
        verifyThat(SESSION_NAME_ID, hasText("Session: Test"));
    }

    @Test
    public void interviewsDisplayedCorrectly() {
        verifyThat(INTERVIEWS_ID, hasText("  - 0 / 2 interviewed"));
    }

    @Test
    public void finalisedStatusDisplayedCorrectly() {
        verifyThat(FINALISE_ID, hasText("  - Rubrics NOT Finalised"));
    }

}
