package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD;

import seedu.address.logic.commands.EditIntervieweeCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new EditIntervieweeCommand object
 */
public class EditIntervieweeCommandParser implements Parser<EditIntervieweeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditIntervieweeCommand
     * and returns an EditIntervieweeCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditIntervieweeCommand parse(String arguments) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arguments, PREFIX_OLD, PREFIX_NEW);

        if (!argMultimap.arePrefixesPresent(PREFIX_OLD, PREFIX_NEW)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditIntervieweeCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getValue(PREFIX_OLD).get().equals("") || argMultimap.getValue(PREFIX_NEW).get().equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditIntervieweeCommand.MESSAGE_USAGE));
        }

        return new EditIntervieweeCommand(
                argMultimap.getValue(PREFIX_OLD).get(),
                argMultimap.getValue(PREFIX_NEW).get()
        );
    }
}
