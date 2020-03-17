package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Question;

/**
 * EditQuestionCommand describes the behavior when the
 * client wants to update a question from the list.
 */

public class EditQuestionCommand extends EditCommand {
    public static final String COMMAND_WORD = "question";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the question identified by its index number in the question list.\n"
            + "Parameters: INDEX UPDATED_DESCRIPTION\n"
            + "Example: update " + COMMAND_WORD + " 1 what the heck is this question?";

    public static final String MESSAGE_EDIT_QUESTION_SUCCESS = "Successfully edited question: %s to %s";
    public static final String MESSAGE_EDIT_INDEX_OUT_OF_BOUND = "The index is out of bound: %s";
    public static final String MESSAGE_EDIT_INDEX_NOT_A_NUMBER = "The index is not a number: %s";

    private final String questionIndex;
    private final String updatedDescription;

    public EditQuestionCommand(String questionIndex, String updatedDescription) {
        this.questionIndex = questionIndex;
        this.updatedDescription = updatedDescription;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Question> questions = model.getQuestionListView();
        try {
            int index = Integer.parseInt(questionIndex);

            if (index > questions.size() || index <= 0) {
                throw new CommandException(String.format(MESSAGE_EDIT_INDEX_OUT_OF_BOUND, questionIndex));
            }

            Question question = questions.get(index - 1);
            Question updated = new Question(updatedDescription);
            questions.set(index - 1, updated);
            return new CommandResult(String.format(MESSAGE_EDIT_QUESTION_SUCCESS, questionIndex, updated),
                    ToggleView.QUESTION);
        } catch (NumberFormatException e) {
            throw new CommandException(String.format(MESSAGE_EDIT_INDEX_NOT_A_NUMBER, questionIndex));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditQuestionCommand // instanceof handles nulls
                && questionIndex.equals(((EditQuestionCommand) other).questionIndex)
                && updatedDescription.equals(((EditQuestionCommand) other).updatedDescription)); // state check
    }
}
