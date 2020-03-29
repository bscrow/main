package seedu.address.model.hirelah;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

class RemarkListTest {

    @Test
    void getRemarks_retrieveRemarks_success() {
        RemarkList actualRemarks = new RemarkList(3);
        assertTrue(actualRemarks.getRemarks().isEmpty());
    }

    @Test
    void add_addRemarks_success() {
        RemarkList actualRemarks = new RemarkList(3);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITHOUT_QUESTION);
        ObservableList<Remark> expectedArray =
                FXCollections.observableArrayList(RemarkTest.REMARK_MIDDLE_WITHOUT_QUESTION);
        assertEquals(expectedArray, actualRemarks.getRemarks());
    }


    @Test
    void getIndexAtTime_getMiddleRemark_success() {
        RemarkList actualRemarks = new RemarkList(3);
        actualRemarks.add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_QUARTER_WITH_QUESTION_1);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION_2);
        actualRemarks.add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        int result = actualRemarks.getIndexAtTime(RemarkTest.DEFAULT_MIDDLE_INSTANT);
        assertEquals(actualRemarks.getRemarks().indexOf(RemarkTest.REMARK_MIDDLE_WITH_QUESTION_2), result);
    }

    @Test
    void isQuestionAnswered_noAnswer_success() {
        RemarkList actualRemarks = new RemarkList(3);
        actualRemarks.add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_QUARTER_WITH_QUESTION_1);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION_2);
        actualRemarks.add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        assertFalse(actualRemarks.isQuestionAnswered(3));
    }

    @Test
    void getInstantOfQuestion_firstQuestion_success() throws IllegalActionException, IllegalValueException {
        RemarkList actualRemarks = new RemarkList(3);
        actualRemarks.add(RemarkTest.REMARK_START_WITHOUT_QUESTION);
        actualRemarks.add(RemarkTest.REMARK_QUARTER_WITH_QUESTION_1);
        actualRemarks.add(RemarkTest.REMARK_MIDDLE_WITH_QUESTION_2);
        actualRemarks.add(RemarkTest.REMARK_STOP_WITHOUT_QUESTION);
        assertEquals(2, actualRemarks.getIndexOfQuestion(2));
    }
}
