package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * ListSessionCommand describes the behavior when the
 * client wants to list the sessions.
 */

public class ListSessionCommand extends Command {
    public static final String COMMAND_WORD = "session";
    public static final String MESSAGE_SUCCESS = "Here is the list of sessions:";
    public static final String MESSAGE_USAGE = "list " + COMMAND_WORD + ": List the sessions that has been created.\n"
            + "Example: list " + COMMAND_WORD;

    /**
     * Creates a ListSessionCommand to list the {@code Session}.
     */
    public ListSessionCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, ToggleView.SESSION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListSessionCommand);
    }
}
