package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.IntervieweeList;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

/**
 * AddIntervieweeCommand describes the behavior when the
 * client wants to add an interviewee to the list.
 */

public class AddIntervieweeCommand extends Command {
    public static final String PREFIX_ALIAS = "-a";

    public static final String COMMAND_WORD = "interviewee";
    public static final String MESSAGE_SUCCESS = "New interviewee added: %1$s";
    public static final String MESSAGE_USAGE = "add " + COMMAND_WORD
            + ": Adds an interviewee to the Interviewee list.\n"
            + "Parameters: "
            + "NAME "
            + PREFIX_ALIAS + " ALIAS\n"
            + "Example: new " + COMMAND_WORD
            + "Jane Doe "
            + PREFIX_ALIAS + " Doe";

    public static final String EMPTY_STRING = "";
    private final String fullname;
    private final String alias;

    /**
     * Creates an AddIntervieweeCommand to add the specified {@code Interviewee}
     */
    public AddIntervieweeCommand(String fullname, String... optionalAlias) {
        this.fullname = fullname;
        if (optionalAlias.length != 0) {
            this.alias = optionalAlias[0];
        } else {
            this.alias = "";
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        IntervieweeList interviewees = model.getIntervieweeList();

        try {
            if (isEmptyAlias()) {
                interviewees.addInterviewee(fullname);
            } else {
                interviewees.addIntervieweeWithAlias(fullname, alias);
            }
        } catch (IllegalActionException | IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fullname), ToggleView.INTERVIEWEE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddIntervieweeCommand // instanceof handles nulls
                && fullname.equals(((AddIntervieweeCommand) other).fullname)
                && alias.equals(((AddIntervieweeCommand) other).alias));
    }

    private boolean isEmptyAlias() {
        return this.alias.equals(EMPTY_STRING);
    }
}
