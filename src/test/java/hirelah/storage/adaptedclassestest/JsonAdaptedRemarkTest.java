package hirelah.storage.adaptedclassestest;

import static hirelah.storage.JsonAdaptedRemark.MESSAGE_CONSTRAINTS;
import static hirelah.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.storage.JsonAdaptedRemark;


public class JsonAdaptedRemarkTest {
    private static final Duration INVALID_TIME = null;
    private static final String INVALID_MESSAGE = null;
    private static final Duration VALID_TIME = Duration.ofMinutes(3);
    private static final String VALID_MESSAGE = "He took CS2103 before and got an A+.";

    @Test
    public void toModelType_validMessage_returnsMessage() throws Exception {
        JsonAdaptedRemark remark = new JsonAdaptedRemark(VALID_TIME, VALID_MESSAGE, 1);
        assertEquals(VALID_MESSAGE, remark.getMessage());
    }

    @Test
    public void toModelType_invalidMessage_throwsIllegalValueException() throws Exception {
        JsonAdaptedRemark remark = new JsonAdaptedRemark(VALID_TIME, INVALID_MESSAGE, 1);
        String expectedMessage = String.format(MESSAGE_CONSTRAINTS, "message");
        assertThrows(IllegalValueException.class, expectedMessage, remark::getMessage);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() throws Exception {
        JsonAdaptedRemark remark = new JsonAdaptedRemark(INVALID_TIME, VALID_MESSAGE, 1);
        String expectedMessage = String.format(MESSAGE_CONSTRAINTS, "time");
        assertThrows(IllegalValueException.class, expectedMessage, remark::getTime);
    }
}
