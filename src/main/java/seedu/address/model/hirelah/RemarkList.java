package seedu.address.model.hirelah;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

/**
 * A list of remarks that are associated  with a particular interview session of an {@code Interviewee}.
 * Some remarks are associated with a question, as it is part of the answer to a question, and some remarks do not.
 * Both are able to be inserted to this remark list. Once a remark has been added, it cannot be removed from the list.
 * An interview session always have a start remark and an end remark, thus it minimally has 2 {@code Remark} objects.
 * It is assumed that there are no 2 Remarks at any particular time.
 *
 * Supports a minimal set of list operations.
 *
 * @see Remark
 */
public class RemarkList {
    private final ObservableList<Remark> remarks = FXCollections.observableArrayList();
    private final int[] questionIndices;

    public RemarkList(int questionsCount) {
        questionIndices = new int[questionsCount + 1];
    }

    /**
     * Retrieves the remark list encapsulated by {@code RemarkList}.
     */
    public ObservableList<Remark> getRemarks() {
        return remarks;
    }

    /**
     * Adds a remark to the list.
     *
     * @param toAdd The remark to be added to the list.
     */
    public void add(Remark toAdd) {
        requireNonNull(toAdd);
        if (toAdd.getQuestionNumber() != null) {
            if (!isQuestionAnswered(toAdd.getQuestionNumber())) {
                questionIndices[toAdd.getQuestionNumber()] = remarks.size();
            }
        }
        remarks.add(toAdd);
    }

    /**
     * Retrieves the number of {@code Remark}s in {@code RemarkList}.
     *
     * @return The number of {@code Remark}s in this {@code RemarkList}.
     */
    private int getRemarkListSize() {
        return this.remarks.size();
    }


    /**
     * Retrieves index of {@code Remark} created around the given time.
     *
     * @param time Time queried.
     * @return The index of the {@code Remark} at or just after time, or the last index
     *         if time exceeds the interview duration.
     */
    public int getIndexAtTime(Duration time) {
        return IntStream.range(0, getRemarkListSize())
                .filter(i -> remarks.get(i).getTime().compareTo(time) >= 0)
                .findFirst()
                .orElse(getRemarkListSize() - 1);
    }

    /**
     * Checks if a question is answered during the interview
     * by checking whether there is a {@code Remark} that is associated with it.
     *
     * @param questionNumber Question that is checked against.
     * @return Whether the question has {@code Remark} associated with it.
     */
    public boolean isQuestionAnswered(int questionNumber) {
        try {
            return questionIndices[questionNumber] != 0;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Retrieves the index of the Remark when this {@code Question}
     * was first asked.
     *
     * @param questionNumber Question number that is queried.
     * @return The index of the {@code Remark} in the RemarkList
     *         that was first associated with this {@code Question}.
     */
    public int getIndexOfQuestion(int questionNumber) throws IllegalActionException {
        if (!isQuestionAnswered(questionNumber)) {
            throw new IllegalActionException("This question was not answered!");
        }
        return questionIndices[questionNumber];
    }
}
