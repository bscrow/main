package seedu.address.model.hirelah;

import java.time.Duration;
import java.time.Instant;

/**
 * InterviewSession manages transient interview state that does not persist between interviews
 * such as the interview start time, and the questions being answered during the interview.
 */
public class InterviewSession {
    private Instant interviewStartTime;
    private int currentQuestionNumber;

    public InterviewSession() {
        this.interviewStartTime = Instant.now();
    }

    public void startQuestion(int questionNumber) {
        this.currentQuestionNumber = questionNumber;
    }

    public void endQuestion() {
        this.currentQuestionNumber = 0;
    }

    /**
     * Creates a remark with extra information from the current interview session context.
     *
     * @param remark The remark that was made.
     * @return A Remark object containing the remark itself, the time in the interview at which it was made,
     *         and the question it is answering to, if a question is active.
     */
    public Remark createRemark(String remark) {
        Duration time = Duration.between(interviewStartTime, Instant.now());
        return currentQuestionNumber == 0
                ? new Remark(time, remark)
                : new Remark(time, remark, currentQuestionNumber);
    }
}
