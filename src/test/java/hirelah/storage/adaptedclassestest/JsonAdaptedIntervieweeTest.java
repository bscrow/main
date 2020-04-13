package hirelah.storage.adaptedclassestest;

import static hirelah.model.hirelah.Interviewee.MESSAGE_CONSTRAINTS;
import static hirelah.storage.storagetests.IntervieweeStorageTest.TRANSCRIPT_STORAGE;
import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalAttributes.getTypicalAttributes;
import static hirelah.testutil.TypicalInterviewee.getAnInterviewee;
import static hirelah.testutil.TypicalQuestions.getTypicalQns;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.storage.JsonAdaptedInterviewee;

public class JsonAdaptedIntervieweeTest {
    private static final boolean TRANSCRIPT = false;
    private static final String RESUME = null;
    private static final int VALID_ID = 1;
    private static final boolean FINALISED = false;

    private static String validname;
    private static String validalias;

    private static final String INVALID_NAME = "123";
    private static final String INVALID_ALIAS = "";
    static {
        try {
            validname = getAnInterviewee().getFullName();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            validalias = getAnInterviewee().getAlias().get();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toModelType_invalidAlias_throwsIllegalValueException() throws Exception {
        JsonAdaptedInterviewee adaptedinterviewee = new JsonAdaptedInterviewee(validname, VALID_ID,
                INVALID_ALIAS, RESUME, TRANSCRIPT);
        String expectedMessage = MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                adaptedinterviewee.toModelType(getTypicalQns(), getTypicalAttributes(),
                        TRANSCRIPT_STORAGE, FINALISED));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() throws Exception {
        JsonAdaptedInterviewee adaptedinterviewee = new JsonAdaptedInterviewee(INVALID_NAME, VALID_ID,
                validname, RESUME, TRANSCRIPT);
        String expectedMessage = MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                adaptedinterviewee.toModelType(getTypicalQns(), getTypicalAttributes(),
                        TRANSCRIPT_STORAGE, FINALISED));
    }

    @Test
    public void toModelType_validInput_returnInterviewee() throws Exception {
        JsonAdaptedInterviewee adaptedinteriewee = new JsonAdaptedInterviewee(validname, VALID_ID, validalias,
                RESUME, TRANSCRIPT);
        assertEquals(getAnInterviewee(), adaptedinteriewee.toModelType(getTypicalQns(), getTypicalAttributes(),
                TRANSCRIPT_STORAGE , FINALISED));
    }
}
