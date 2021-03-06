package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.EditAttributeCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditIntervieweeCommand;
import seedu.address.logic.commands.EditQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final Pattern BASIC_EDIT_COMMAND_FORMAT =
            Pattern.compile("(?<editCommandWord>\\S+) (?<editArguments>.+)");

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String arguments) throws ParseException {
        Matcher matcher = BASIC_EDIT_COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        final String editCommandWord = matcher.group("editCommandWord");
        final String editArguments = matcher.group("editArguments");

        switch (editCommandWord) {
        case EditAttributeCommand.COMMAND_WORD:
            return new EditAttributeCommandParser().parse(arguments);

        case EditIntervieweeCommand.COMMAND_WORD:
            return new EditIntervieweeCommandParser().parse(arguments);

        case EditQuestionCommand.COMMAND_WORD:
            return new EditQuestionCommandParser().parse(editArguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
