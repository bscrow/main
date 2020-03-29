package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAttributeCommand;
import seedu.address.logic.commands.AddIntervieweeCommand;
import seedu.address.logic.commands.AddMetricCommand;
import seedu.address.logic.commands.AddQuestionCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new object of type AddCommand
 */
public class AddCommandParser implements Parser<Command> {

    private static final Pattern BASIC_ADD_COMMAND_FORMAT =
            Pattern.compile("(?<addCommandWord>\\S+) (?<addArguments>.+)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String arguments) throws ParseException {
        Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(arguments.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_COMMAND));
        }

        final String addCommandWord = matcher.group("addCommandWord");
        final String addArguments = matcher.group("addArguments");

        switch (addCommandWord) {
        case AddAttributeCommand.COMMAND_WORD:
            ParserUtil.checkEmptyArgument(AddAttributeCommand.MESSAGE_USAGE);
            return new AddAttributeCommand(addArguments.trim());

        case AddIntervieweeCommand.COMMAND_WORD:
            return new AddIntervieweeCommandParser().parse(addArguments.trim());

        case AddQuestionCommand.COMMAND_WORD:
            ParserUtil.checkEmptyArgument(AddQuestionCommand.MESSAGE_USAGE);
            return new AddQuestionCommand(addArguments.trim());

        case AddMetricCommand.COMMAND_WORD:
            return new AddMetricCommandParser().parse(addArguments.trim());

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
