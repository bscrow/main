package seedu.address.model.hirelah;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Duration;

import org.junit.jupiter.api.Test;

class RemarkTest {
    public static final Duration DEFAULT_EARLIER_INSTANT = Duration.ofSeconds(0);
    public static final Duration DEFAULT_QUARTER_INSTANT = DEFAULT_EARLIER_INSTANT.plusMillis(250);
    public static final Duration DEFAULT_MIDDLE_INSTANT = DEFAULT_EARLIER_INSTANT.plusMillis(500);
    public static final Duration DEFAULT_LATER_INSTANT = DEFAULT_EARLIER_INSTANT.plusMillis(1000);
    public static final Remark REMARK_START_WITHOUT_QUESTION = new Remark(DEFAULT_EARLIER_INSTANT, "Start");
    public static final Remark REMARK_START_WITH_QUESTION_1 = new Remark(DEFAULT_EARLIER_INSTANT, "Start", 1);
    public static final Remark REMARK_MIDDLE_WITHOUT_QUESTION = new Remark(DEFAULT_MIDDLE_INSTANT, "Random remark");
    public static final Remark REMARK_STOP_WITHOUT_QUESTION = new Remark(DEFAULT_LATER_INSTANT, "Stop");
    public static final Remark REMARK_QUARTER_WITH_QUESTION_1 = new Remark(DEFAULT_QUARTER_INSTANT,
            "Random remark", 1);
    public static final Remark REMARK_MIDDLE_WITH_QUESTION_2 = new Remark(DEFAULT_MIDDLE_INSTANT,
            "Random remark", 2);

    @Test
    void getTime_maxTime_success() {
        assertEquals(DEFAULT_MIDDLE_INSTANT, REMARK_MIDDLE_WITHOUT_QUESTION.getTime());
    }

    @Test
    void getQuestion_noQuestion_nullReturned() {
        assertNull(REMARK_MIDDLE_WITHOUT_QUESTION.getQuestionNumber());
    }

    @Test
    void getTime_withQuestion_success() {
        assertEquals(DEFAULT_MIDDLE_INSTANT, REMARK_MIDDLE_WITH_QUESTION_2.getTime());
    }

    @Test
    void getQuestion_withQuestion_success() {
        assertEquals(2, REMARK_MIDDLE_WITH_QUESTION_2.getQuestionNumber());
    }

    @Test
    void equals_sameTimeAndMessageDifferentQuestion_false() {
        assertNotEquals(REMARK_START_WITH_QUESTION_1, REMARK_START_WITHOUT_QUESTION);
    }
}
