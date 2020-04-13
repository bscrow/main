package hirelah.storage.adaptedclassestest;

import static hirelah.storage.JsonAdaptedAttributes.MESSAGE_CONSTRAINTS;
import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalAttributes.getAnAttribute;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.storage.JsonAdaptedAttributes;

public class JsonAdaptedAttributesTest {
    public static final String VALID_ATTRIBUTE = getAnAttribute().toString();
    public static final String INVALID_ATTRIBUTE = "L@adership";

    @Test
    public void toModelType_invalidAttribute_throwsIllegalValueException() {
        JsonAdaptedAttributes attribute = new JsonAdaptedAttributes(INVALID_ATTRIBUTE);
        String expectedMessage = MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, attribute::toModelType);
    }

    @Test
    public void toModelType_validAttribute_throwsIllegalValueException() throws Exception {
        JsonAdaptedAttributes attribute = new JsonAdaptedAttributes(VALID_ATTRIBUTE);
        assertEquals(getAnAttribute(), attribute.toModelType());
    }
}
