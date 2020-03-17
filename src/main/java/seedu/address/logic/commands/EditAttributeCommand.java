package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Attribute;

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

    public static final String MESSAGE_EDIT_ATTRIBUTE_SUCCESS = "Successfully edited Attribute: %1$s to %1$s";
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
        ObservableList<Attribute> attributes = model.getAttributeListView();
        Attribute attribute = find(attributes);
        Attribute updated = new Attribute(updatedAttribute);
        int index = attributes.indexOf(attribute);
        attributes.set(index, updated);
        return new CommandResult(String.format(MESSAGE_EDIT_ATTRIBUTE_SUCCESS, attribute, updated),
                ToggleView.ATTRIBUTE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditAttributeCommand // instanceof handles nulls
                && attributePrefix.equals(((EditAttributeCommand) other).attributePrefix)
                && updatedAttribute.equals(((EditAttributeCommand) other).updatedAttribute)); // state check
    }

    /**
     * Find the attribute based on its prefix.
     * @param attributes The list of attributes.
     * @return The corresponding Attribute instance.
     * @throws CommandException if the prefix can be multi-interpreted or no such Attribute found.
     */

    private Attribute find(ObservableList<Attribute> attributes) throws CommandException {
        checkPrefix(attributes);
        return attributes.stream().filter(attribute -> attribute.toString().startsWith(attributePrefix))
                .findFirst()
                .get();
    }

    /**
     * Checks the number of attributes that starts with the prefix.
     * @param attributes The list of the attributes
     * @throws CommandException if the prefix can be multi-interpreted or no such Attribute found.
     */
    private void checkPrefix(ObservableList<Attribute> attributes) throws CommandException {
        long startWithPrefix = attributes.stream()
                .filter(attribute -> attribute.toString().startsWith(attributePrefix))
                .count();

        if (startWithPrefix > 1) {
            throw new CommandException(String.format(MESSAGE_EDIT_DUPLICATE_PREFIX, attributePrefix));
        } else if (startWithPrefix == 0) {
            throw new CommandException(String.format(MESSAGE_EDIT_NO_PREFIX_FOUND, attributePrefix));

        }
    }
}
