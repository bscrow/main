package hirelah.model.hirelah;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;

class IntervieweeTest {
    private Transcript transcriptCompleted = new Transcript(new QuestionList(), new AttributeList());
    private Transcript transcriptNotCompleted = new Transcript(new QuestionList(), new AttributeList());
    {
        transcriptCompleted.complete();
    }

    @Test
    void constructor_invalidName_exceptionThrown() {
        try {
            Interviewee i = new Interviewee("12345", 4); // test numeric name
            fail();
        } catch (IllegalValueException e) {
            assertEquals(Interviewee.MESSAGE_CONSTRAINTS, e.getMessage());
        }
        try {
            Interviewee i = new Interviewee("", 4); // test empty string name
            fail();
        } catch (IllegalValueException e) {
            assertEquals(Interviewee.MESSAGE_CONSTRAINTS, e.getMessage());
        }
    }

    @Test
    void setAlias_validIdentifier_hasAlias() throws IllegalValueException {
        Interviewee i = new Interviewee("Bob", 4);
        i.setAlias("Bo");
        assertTrue(i.getAlias().isPresent());
    }

    @Test
    void isInterviewed_notStarted_false() throws IllegalValueException {
        Interviewee i = new Interviewee("Bob", 4);
        assertFalse(i.isInterviewed());
    }

    @Test
    void isInterviewed_startedNotComplete_false() throws IllegalValueException {
        Interviewee i = new Interviewee("Bob", 4);
        i.setTranscript(transcriptNotCompleted);
        assertFalse(i.isInterviewed());
    }

    @Test
    void isInterviewed_complete_true() throws IllegalValueException {
        Interviewee i = new Interviewee("Bob", 4);
        i.setTranscript(transcriptCompleted);
        assertTrue(i.isInterviewed());
    }
}
