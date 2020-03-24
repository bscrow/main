package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.FinaliseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FinaliseCommand object
 */
public class FinaliseCommandParser implements Parser<FinaliseCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FinaliseCommand
     * and returns a FinaliseCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public FinaliseCommand parse(String arguments) throws ParseException {
        if (!arguments.equals("")) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND.trim());
        }
        return new FinaliseCommand();
    }

}
