package hirelah.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import hirelah.commons.exceptions.IllegalValueException;

/**
 * A class to represent Model state in JSON.
 */
public class JsonSerializableModel {
    private final Boolean finalised;

    @JsonCreator
    public JsonSerializableModel(@JsonProperty("finalised") Boolean finalised) { //potential error
        this.finalised = finalised;
    }

    /**convert the Boolean from json format back to Boolean format*/
    public Boolean toModelType() throws IllegalValueException {
        if (this.finalised == null || (this.finalised != true && this.finalised != false)) {
            throw new IllegalValueException("The interview state has been corrupted!");
        }
        return this.finalised;
    }
}
