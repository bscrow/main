package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Attribute;

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

    public static final String MESSAGE_DELETE_ATTRIBUTE_SUCCESS = "Deleted Attribute: %1$s";
    public static final String MESSAGE_DELETE_DUPLICATE_PREFIX = "There are multiple attributes with prefix: %s";
    public static final String MESSAGE_DELETE_NO_PREFIX_FOUND = "There is no attribute with the given prefix: %s";

    private final String attributePrefix;

    public DeleteAttributeCommand(String attributePrefix) {
        this.attributePrefix = attributePrefix;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Attribute> attributes = model.getAttributeListView();
        Attribute attribute = find(attributes);
        attributes.remove(attribute);
        return new CommandResult(String.format(MESSAGE_DELETE_ATTRIBUTE_SUCCESS, attribute), ToggleView.ATTRIBUTE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAttributeCommand // instanceof handles nulls
                && attributePrefix.equals(((DeleteAttributeCommand) other).attributePrefix)); // state check
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
            throw new CommandException(String.format(MESSAGE_DELETE_DUPLICATE_PREFIX, attributePrefix));
        } else if (startWithPrefix == 0) {
            throw new CommandException(String.format(MESSAGE_DELETE_NO_PREFIX_FOUND, attributePrefix));

        }
    }
}
