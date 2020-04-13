package hirelah.storage.adaptedclassestest;

import static hirelah.storage.JsonAdaptedQuestions.MESSAGE_CONSTRAINTS;
import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalQuestions.getAQuestion;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.storage.JsonAdaptedQuestions;

public class JsonAdaptedQuestionsTest {
    private static final String INVALID_QUESTION = null;

    @Test
    public void toModelType_validQuestion_returnsQuestion() throws Exception {
        JsonAdaptedQuestions question = new JsonAdaptedQuestions(getAQuestion());
        assertEquals(getAQuestion(), question.toModelType());
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() throws Exception {
        JsonAdaptedQuestions question = new JsonAdaptedQuestions(INVALID_QUESTION);
        String expectedMessage = MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }
}
