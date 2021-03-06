package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.ListAttributeCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListIntervieweeCommand;
import seedu.address.logic.commands.ListQuestionCommand;
import seedu.address.logic.commands.ListSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input command and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of commandWord in the context of the ListCommand
     * and returns an ListCommand object for execution.
     *
     * @param commandWord the command word to be parsed
     * @throws ParseException if the user input is not a valid command word
     */
    public ListCommand parse(String commandWord) throws ParseException {

        final String listCommandWord = commandWord.trim().toLowerCase();

        switch (listCommandWord) {
        case ListAttributeCommand.COMMAND_WORD:
            return new ListAttributeCommand();

        case ListIntervieweeCommand.COMMAND_WORD:
            return new ListIntervieweeCommand();

        case ListQuestionCommand.COMMAND_WORD:
            return new ListQuestionCommand();

        case ListSessionCommand.COMMAND_WORD:
            return new ListSessionCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
