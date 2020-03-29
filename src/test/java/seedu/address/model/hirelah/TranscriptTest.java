package seedu.address.model.hirelah;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

class TranscriptTest {
    Transcript getTranscript() throws IllegalValueException {
        QuestionList questions = new QuestionList();
        questions.add("One");
        questions.add("Two");
        return new Transcript(questions);
    }

    @Test
    void getTranscriptAtTime_beyondLastTime_returnLastIndex() throws IllegalValueException {
        Transcript actualTranscript = getTranscript();
        actualTranscript.addRemark(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualTranscript.addRemark(RemarkTest.REMARK_QUARTER_WITH_QUESTION_1);
        assertEquals(1, actualTranscript.getIndexAtTime(Duration.ofSeconds(10000)));
    }

    @Test
    void getTranscriptAtTime_validTimeRange_success() throws IllegalValueException {
        Transcript actualTranscript = getTranscript();
        actualTranscript.addRemark(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualTranscript.addRemark(RemarkTest.REMARK_MIDDLE_WITH_QUESTION_2);
        actualTranscript.addRemark(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);

        assertEquals(1, actualTranscript.getIndexAtTime(RemarkTest.DEFAULT_MIDDLE_INSTANT));
    }

    @Test
    void getTranscriptAtQuestion_unansweredQuestion_failure() throws IllegalValueException {
        Transcript actualTranscript = getTranscript();
        assertThrows(IllegalActionException.class, () ->
                actualTranscript.getIndexOfQuestion(1));
    }

    @Test
    void getTranscriptAtQuestion_answeredQuestion_success() throws IllegalActionException, IllegalValueException {
        Transcript actualTranscript = getTranscript();
        actualTranscript.addRemark(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualTranscript.addRemark(RemarkTest.REMARK_QUARTER_WITH_QUESTION_1);
        actualTranscript.addRemark(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        assertEquals(1, actualTranscript.getIndexOfQuestion(1));
    }

}
