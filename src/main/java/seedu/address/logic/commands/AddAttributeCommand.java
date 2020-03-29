package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.AttributeList;

/**
 * AddAttributeCommand describes the behavior when the
 * client wants to add an attribute to the list.
 */

public class AddAttributeCommand extends Command {
    public static final String COMMAND_WORD = "attribute";
    public static final String MESSAGE_SUCCESS = "New attribute added: %1$s";
    public static final String MESSAGE_USAGE = "add " + COMMAND_WORD + ": Adds an attribute to the Attribute list.\n"
            + "Parameters: NAME\n"
            + "Example: new " + COMMAND_WORD + " leadership";


    private final String toAdd;

    /**
     * Creates an AddAttributeCommand to add the specified {@code Attribute}
     */
    public AddAttributeCommand(String attribute) {
        toAdd = attribute;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AttributeList attributes = model.getAttributeList();

        try {
            if (model.isFinalisedInterviewProperties()) {
                throw new CommandException("The interview session's attributes has been finalised."
                        + " You can no longer add a new attribute.");
            }
            attributes.add(toAdd);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), ToggleView.ATTRIBUTE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAttributeCommand // instanceof handles nulls
                && toAdd.equals(((AddAttributeCommand) other).toAdd));
    }
}
