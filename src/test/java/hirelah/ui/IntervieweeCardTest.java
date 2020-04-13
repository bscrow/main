package hirelah.ui;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

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
import hirelah.model.hirelah.Interviewee;
import hirelah.testutil.TypicalInterviewee;
import hirelah.testutil.TypicalTranscript;

import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class IntervieweeCardTest {
    private static final String NAME_ID = "#name";
    private static final String ID_ID = "#id";
    private static final String ALIAS_ID = "#alias";


    @AfterEach
    public void pause(FxRobot robot) {
        robot.sleep(300);
    }

    /**
     * Initialise InterviewCard test with a stage.
     *
     * @param stage Stage used to test.
     */
    @Start
    public void start(Stage stage) throws IllegalValueException {

        Interviewee testInterviewee = TypicalInterviewee.getAnInterviewee();
        testInterviewee.setTranscript(TypicalTranscript.getTypicalTranscript());
        Scene scene = new Scene(new IntervieweeCard(testInterviewee).getRoot());
        scene.getStylesheets().add("/view/DarkTheme.css");
        stage.setScene(scene);
        stage.show();
    }

    @Stop
    void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Test
    public void nameIsDisplayedCorrectly() throws IllegalValueException {
        Interviewee testInterviewee = TypicalInterviewee.getAnInterviewee();
        testInterviewee.setTranscript(TypicalTranscript.getTypicalTranscript());
        verifyThat(NAME_ID, hasText(testInterviewee.getFullName()));
    }

    @Test
    public void idIsDisplayedCorrectly() throws IllegalValueException {
        Interviewee testInterviewee = TypicalInterviewee.getAnInterviewee();
        testInterviewee.setTranscript(TypicalTranscript.getTypicalTranscript());
        verifyThat(ID_ID, hasText("ID:         " + testInterviewee.getId()));
    }

    @Test
    public void aliasIsDisplayedCorrectly() throws IllegalValueException {
        Interviewee testInterviewee = TypicalInterviewee.getAnInterviewee();
        testInterviewee.setTranscript(TypicalTranscript.getTypicalTranscript());
        verifyThat(ALIAS_ID, hasText("Alias:     lucky boy"));
    }

}
