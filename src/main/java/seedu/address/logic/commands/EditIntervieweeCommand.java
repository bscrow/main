package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.IntervieweeList;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

/**
 * EditIntervieweeCommand describes the behavior when the
 * client wants to update an intervewee's name from the list.
 */

public class EditIntervieweeCommand extends EditCommand {
    public static final String COMMAND_WORD = "interviewee";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the name of the interviewee identified by the identifier.\n"
            + "Parameters: IDENTIFIER NAME\n"
            + "Example: update " + COMMAND_WORD + " Doe Mario Lorenzo";

    public static final String MESSAGE_EDIT_INTERVIEWEE_SUCCESS = "Successfully edited Interviewee: %1$s to %1$s";
    public static final String MESSAGE_EDIT_DUPLICATE_PREFIX = "There are multiple interviewees with identifier: %s";

    private final String identifier;
    private final String updatedName;

    public EditIntervieweeCommand(String identifier, String updatedName) {
        this.identifier = identifier;
        this.updatedName = updatedName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        IntervieweeList interviewees = model.getIntervieweeList();
        try {
            interviewees.getInterviewee(identifier).setFullName(updatedName);

        } catch (IllegalActionException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_EDIT_INTERVIEWEE_SUCCESS, identifier, updatedName),
                ToggleView.INTERVIEWEE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditIntervieweeCommand // instanceof handles nulls
                && identifier.equals(((EditIntervieweeCommand) other).identifier)
                && updatedName.equals(((EditIntervieweeCommand) other).updatedName)); // state check
    }

}
