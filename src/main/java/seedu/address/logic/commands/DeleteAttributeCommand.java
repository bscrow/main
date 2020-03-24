package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Attribute;
import seedu.address.model.hirelah.AttributeList;

/**
 * DeleteAttributeCommand describes the behavior when the
 * client wants to delete an attribute from the list.
 */

public class DeleteAttributeCommand extends DeleteCommand {
    public static final String COMMAND_WORD = "attribute";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the attribute identified by its prefix.\n"
            + "Parameters: PREFIX\n"
            + "Example: delete " + COMMAND_WORD + " lea";

    public static final String MESSAGE_DELETE_ATTRIBUTE_SUCCESS = "Deleted Attribute with prefix: %1$s";

    private final String attributePrefix;

    public DeleteAttributeCommand(String attributePrefix) {
        this.attributePrefix = attributePrefix;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AttributeList attributes = model.getAttributeList();
        try {
            if (model.isfinalisedInterviewProperties()) {
                throw new CommandException("The interview session's attributes has been finalised."
                        + " You can no longer delete an attribute.");
            }
            Attribute attribute = attributes.delete(attributePrefix);
            return new CommandResult(String.format(MESSAGE_DELETE_ATTRIBUTE_SUCCESS,
                    attribute), ToggleView.ATTRIBUTE);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAttributeCommand // instanceof handles nulls
                && attributePrefix.equals(((DeleteAttributeCommand) other).attributePrefix)); // state check
    }
}
