package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Attribute;
import seedu.address.model.hirelah.AttributeList;

/**
 * EditAttributeCommand describes the behavior when the
 * client wants to update an attribute from the list.
 */

public class EditAttributeCommand extends EditCommand {
    public static final String COMMAND_WORD = "attribute";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the attribute identified by its prefix.\n"
            + "Parameters: PREFIX NEW_ATTRIBUTE\n"
            + "Example: update " + COMMAND_WORD + " lea tenacity";

    public static final String MESSAGE_EDIT_ATTRIBUTE_SUCCESS = "Successfully edited Attribute with prefix: %s to %s";
    public static final String MESSAGE_EDIT_DUPLICATE_PREFIX = "There are multiple attributes with prefix: %s";
    public static final String MESSAGE_EDIT_NO_PREFIX_FOUND = "There is no attribute with the given prefix: %s";

    private final String attributePrefix;
    private final String updatedAttribute;

    public EditAttributeCommand(String attributePrefix, String updatedAttribute) {
        this.attributePrefix = attributePrefix;
        this.updatedAttribute = updatedAttribute;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AttributeList attributes = model.getAttributeList();
        try {
            if (model.isFinalisedInterviewProperties()) {
                throw new CommandException("The interview session's attributes has been finalised."
                        + " You can no longer edit an attribute.");
            }
            Attribute attribute = attributes.edit(attributePrefix, updatedAttribute);
            return new CommandResult(String.format(MESSAGE_EDIT_ATTRIBUTE_SUCCESS, attribute, updatedAttribute),
                    ToggleView.ATTRIBUTE);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditAttributeCommand // instanceof handles nulls
                && attributePrefix.equals(((EditAttributeCommand) other).attributePrefix)
                && updatedAttribute.equals(((EditAttributeCommand) other).updatedAttribute)); // state check
    }
}
