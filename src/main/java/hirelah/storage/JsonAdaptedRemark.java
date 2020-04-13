package hirelah.storage;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.hirelah.QuestionRemark;
import hirelah.model.hirelah.Remark;

/**
 * A class to represent {@code Remark} and its subclass {@code QuestionRemark} in JSON.
 */
public class JsonAdaptedRemark {
    public static final String MESSAGE_CONSTRAINTS = "Invalid %s!";
    private final Duration time;
    private final String message;
    private final Integer questionNumber;

    @JsonCreator
    public JsonAdaptedRemark(@JsonProperty("time") Duration time, @JsonProperty("message") String message,
                             @JsonProperty("questionNumber") Integer questionNumber) {
        this.time = time;
        this.message = message;
        this.questionNumber = questionNumber;
    }

    public JsonAdaptedRemark(Remark source) {
        time = source.getTime();
        message = source.getMessage();
        if (source instanceof QuestionRemark) {
            questionNumber = ((QuestionRemark) source).getQuestionNumber();
        } else {
            questionNumber = null;
        }
    }

    public Duration getTime() throws IllegalValueException {
        if (time == null) {
            throw new IllegalValueException(String.format(MESSAGE_CONSTRAINTS, "time"));
        }
        return time;
    }

    public String getMessage() throws IllegalValueException {
        if (message == null) {
            throw new IllegalValueException(String.format(MESSAGE_CONSTRAINTS, "message"));
        }
        return message;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }
}
