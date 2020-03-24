package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.QuestionList;

/**
 * AddQuestionCommand describes the behavior when the
 * client wants to add a question to the list.
 */

public class AddQuestionCommand extends AddCommand {
    public static final String COMMAND_WORD = "question";
    public static final String MESSAGE_DUPLICATE_QUESTION = "The question already exists.";
    public static final String MESSAGE_SUCCESS = "New question added: %1$s";
    public static final String MESSAGE_USAGE = "new " + COMMAND_WORD + ": Adds an question to the Question list. "
            + "Parameters: "
            + "DESCRIPTION "
            + "Example: new " + COMMAND_WORD + " what is this question?";


    private final String toAdd;

    /**
     * Creates an AddQuestionCommand to add the specified {@code Question}
     */
    public AddQuestionCommand(String question) {
        toAdd = question;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        QuestionList questions = model.getQuestionList();
        try {
            if (model.isfinalisedInterviewProperties()) {
                throw new CommandException("The interview session's questions has been finalised."
                        + " You can no longer add a new question.");
            }
            questions.add(toAdd);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), ToggleView.QUESTION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddQuestionCommand // instanceof handles nulls
                && toAdd.equals(((AddQuestionCommand) other).toAdd));
    }
}
