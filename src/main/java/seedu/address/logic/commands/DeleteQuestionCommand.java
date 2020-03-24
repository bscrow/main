package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.QuestionList;

/**
 * DeleteQuestionCommand describes the behavior when the
 * client wants to delete a question from the list.
 */

public class DeleteQuestionCommand extends DeleteCommand {
    public static final String COMMAND_WORD = "question";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the question identified by its index number in the question list.\n"
            + "Parameters: INDEX\n"
            + "Example: delete " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_QUESTION_SUCCESS = "Deleted Question: %1$s";
    public static final String MESSAGE_DELETE_INDEX_OUT_OF_BOUND = "The index is out of bound: %s";
    public static final String MESSAGE_DELETE_INDEX_NOT_A_NUMBER = "The index is not a number: %s";

    private final String questionIndex;

    public DeleteQuestionCommand(String questionIndex) {
        this.questionIndex = questionIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        QuestionList questions = model.getQuestionList();
        try {
            if (model.isfinalisedInterviewProperties()) {
                throw new CommandException("The interview session's questions has been finalised."
                        + " You can no longer delete a question.");
            }
            return new CommandResult(String.format(MESSAGE_DELETE_QUESTION_SUCCESS, questions.delete(questionIndex)),
                    ToggleView.QUESTION);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteQuestionCommand // instanceof handles nulls
                && questionIndex.equals(((DeleteQuestionCommand) other).questionIndex)); // state check
    }
}
