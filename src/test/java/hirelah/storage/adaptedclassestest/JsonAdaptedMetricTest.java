package hirelah.storage.adaptedclassestest;

import static hirelah.storage.JsonAdaptedMetric.MESSAGE_CONSTRAINTS;
import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalMetric.getSingleMetric;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.storage.JsonAdaptedMetric;

public class JsonAdaptedMetricTest {
    private static final String VALID_NAME = getSingleMetric().toString();
    private static final String VALID_MAP = getSingleMetric().hashMapToString();
    private static final String INVALID_NAME = "@123";
    private static final String INVALID_MAP = "";

    @Test
    public void toModelType_invalidMetricName_throwsIllegalValueException() {
        JsonAdaptedMetric metric = new JsonAdaptedMetric(INVALID_NAME, VALID_MAP);
        String expectedMessage = String.format(MESSAGE_CONSTRAINTS, "NAME");
        assertThrows(IllegalValueException.class, expectedMessage, metric::toModelType);
    }

    @Test
    public void toModelType_invalidAttributeWeight_throwsIllegalValueException() {
        JsonAdaptedMetric metric = new JsonAdaptedMetric(VALID_NAME, INVALID_MAP);
        String expectedMessage = String.format(MESSAGE_CONSTRAINTS, "pair of Attribute to weight");
        assertThrows(IllegalValueException.class, expectedMessage, metric::toModelType);
    }

    @Test
    public void toModelType_validInput_returnMetric() throws Exception {
        JsonAdaptedMetric metric = new JsonAdaptedMetric(VALID_NAME, VALID_MAP);
        assertEquals(getSingleMetric(), metric.toModelType());
    }
}
