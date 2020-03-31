package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.ModelUtil.validateFinalisation;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Question;
import seedu.address.model.hirelah.QuestionList;

/**
 * EditQuestionCommand describes the behavior when the
 * client wants to update a question from the list.
 */

public class EditQuestionCommand extends Command {
    public static final String COMMAND_WORD = "question";
    public static final boolean DESIRED_MODEL_FINALIZED_STATE = false;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the question identified by its index number in the question list.\n"
            + "Parameters: INDEX UPDATED_DESCRIPTION\n"
            + "Example: update " + COMMAND_WORD + " 1 what the heck is this question?";

    public static final String MESSAGE_EDIT_QUESTION_SUCCESS = "Successfully edited question: %s to %s";

    private final int questionIndex;
    private final String updatedDescription;

    public EditQuestionCommand(int questionIndex, String updatedDescription) {
        this.questionIndex = questionIndex;
        this.updatedDescription = updatedDescription;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        validateFinalisation(model, DESIRED_MODEL_FINALIZED_STATE);
        QuestionList questions = model.getQuestionList();

        try {
            Question question = questions.edit(questionIndex, updatedDescription);
            return new ToggleCommandResult(String.format(MESSAGE_EDIT_QUESTION_SUCCESS, question, updatedDescription),
                    ToggleView.QUESTION);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditQuestionCommand // instanceof handles nulls
                && questionIndex == ((EditQuestionCommand) other).questionIndex
                && updatedDescription.equals(((EditQuestionCommand) other).updatedDescription)); // state check
    }
}
