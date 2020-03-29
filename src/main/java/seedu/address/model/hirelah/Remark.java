package seedu.address.model.hirelah;

import java.time.Duration;

/**
 * Stores the remark message that are inserted by the interviewer
 * at any given time of the interview. It can be a part of an answer to
 * a particular question, or just a normal remark.
 */
public class Remark {
    private final Duration time;
    private final String message;
    private final Integer questionNumber;

    /**
     * Constructs a {@code Remark} which is part of the answer
     * to a question of the interview.
     *
     * @param time The time when the remark was made.
     * @param message The remark message.
     * @param questionNumber The question associated with this remark as its answer.
     */
    Remark(Duration time, String message, Integer questionNumber) {
        this.time = time;
        this.message = message;
        this.questionNumber = questionNumber;
    }

    /**
     * Constructs a {@code Remark} which is not part of the answer
     * to any question of the interview.
     *
     * @param time The time when the remark was made.
     * @param message The remark message.
     */
    Remark(Duration time, String message) {
        this.time = time;
        this.message = message;
        this.questionNumber = null;
    }

    /**
     * Retrieves the time of the interview when this {@code Remark}
     * was created.
     *
     * @return  The Duration since interview start when this {@code Remark} was created.
     */
    public Duration getTime() {
        return time;
    }

    /**
     * Formats the time in a readable format.
     *
     * @return the formatted time string in minutes and seconds.
     */
    public String getTimeString() {
        return String.format("%d:%d", time.toMinutes(), time.toSecondsPart());
    }

    /**
     * Retrieves the Question index associated with this {@code Remark}
     * if there is any.
     *
     * @return The index of the Question associated with this {@code Remark} if there is any.
     */
    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return this.message;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Remark) {
            Remark otherRemark = ((Remark) other);
            boolean isSameQuestion = this.questionNumber == otherRemark.getQuestionNumber() // both null
                    || (this.questionNumber != null
                    && this.questionNumber.equals(otherRemark.getQuestionNumber()));
            return time.equals(otherRemark.getTime())
                    && message.equals(otherRemark.getMessage())
                    && isSameQuestion;
        } else {
            return false;
        }
    }
}
