package hirelah.storage;

import static hirelah.model.hirelah.Attribute.isValidAttributeName;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.hirelah.Attribute;

/**
 * Jackson-friendly version of {@link Attribute}.
 */

public class JsonAdaptedAttributes {
    public static final String MESSAGE_CONSTRAINTS =
            "The attribute saved should only contain alphabet characters and spaces, and it should not be blank";
    public static final String MESSAGE_CONSTRAINT1 = "The attribute saved is empty";
    private final String attribute;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedAttributes(@JsonProperty("attribute") String attribute) {
        this.attribute = attribute;
    }

    public JsonAdaptedAttributes(Attribute attribute) {
        this.attribute = attribute.toString();
    }
    /**
     * Converts this Jackson-friendly adapted Attribute object into the model's {@code Attribute} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attribute.
     */
    public Attribute toModelType() throws IllegalValueException {
        if (attribute == null) {
            throw new IllegalValueException(MESSAGE_CONSTRAINT1);
        }
        if (!isValidAttributeName(attribute)) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }
        return Attribute.of(attribute);
    }
}
