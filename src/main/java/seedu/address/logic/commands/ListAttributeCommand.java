package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * ListAttributeCommand describes the behavior when the
 * client wants to list the attributes.
 */

public class ListAttributeCommand extends ListCommand {
    public static final String COMMAND_WORD = "attribute";
    public static final String MESSAGE_SUCCESS = "Here is the list of attributes:";
    public static final String MESSAGE_USAGE = "list " + COMMAND_WORD + ": List the attribute from the Attribute list. "
            + "Example: list " + COMMAND_WORD;

    /**
     * Creates a ListAttributeCommand to list the {@code Attribute}
     */
    public ListAttributeCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, ToggleView.ATTRIBUTE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListAttributeCommand);
    }
}
