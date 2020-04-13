package hirelah.model;

import static hirelah.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hirelah.commons.core.GuiSettings;
import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.hirelah.AppPhase;
import hirelah.model.hirelah.AttributeList;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.QuestionList;
import hirelah.model.hirelah.Transcript;
import hirelah.model.hirelah.exceptions.IllegalActionException;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    /** Starts a typical session. */
    private void startSession() {
        modelManager.setCurrentSession(Paths.get("session"));
        modelManager.setQuestionList(new QuestionList());
        modelManager.setAttributeList(new AttributeList());
        modelManager.setAppPhase(AppPhase.NORMAL);
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setSessionsDirectory(Paths.get("hirelah/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setSessionsDirectory(Paths.get("new/hirelah/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setSessionsDirectory_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setSessionsDirectory(null));
    }

    @Test
    public void setSessionsDirectory_validPath_setsSessionsDirectory() {
        Path path = Paths.get("hirelah/file/path");
        modelManager.setSessionsDirectory(path);
        assertEquals(path, modelManager.getSessionsDirectory());
    }

    @Test
    public void closeSession_preSessionState() {
        startSession();
        modelManager.finaliseInterviewProperties();
        modelManager.closeSession();
        assertFalse(modelManager.isFinalisedInterviewProperties());
        assertFalse(modelManager.getCurrentSession().isPresent());
        assertEquals(AppPhase.PRE_SESSION, modelManager.getAppPhase());
    }

    @Test
    public void getCurrentTranscript_noInterviewee_exceptionThrown() {
        startSession();
        assertThrows(NullPointerException.class, () -> modelManager.getCurrentTranscript());
    }

    @Test
    public void startInterview_interviewed_exceptionThrown() throws IllegalValueException {
        startSession();
        Interviewee interviewee = new Interviewee("Bob", 1);
        interviewee.setTranscript(new Transcript(new QuestionList(), new AttributeList()));
        interviewee.getTranscript().get().complete();
        assertThrows(IllegalActionException.class, () -> modelManager.startInterview(interviewee));
    }

    @Test
    public void startInterview_notInterviewed_interviewPhaseAndTranscriptPresent()
            throws IllegalValueException, IllegalActionException {
        startSession();
        Interviewee interviewee = new Interviewee("Bob", 1);
        modelManager.startInterview(interviewee);
        assertEquals(AppPhase.INTERVIEW, modelManager.getAppPhase());
        assertDoesNotThrow(() -> modelManager.getCurrentTranscript());
    }
}
