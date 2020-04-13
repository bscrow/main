package hirelah.logic.parser;

import static hirelah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hirelah.logic.commands.Command;
import hirelah.logic.commands.LoadAttributeCommand;
import hirelah.logic.commands.LoadQuestionCommand;
import hirelah.logic.parser.exceptions.ParseException;

/**
 * LoadParser class defines the behavior of the parser
 * that parses LoadAttributeCommand and LoadQuestionCommand
 */
public class LoadParser implements Parser<Command> {
    public static final String TEMPLATE = "%s\n%s";
    private static final String ATTRIBUTE_STRING = "attribute";
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<data>\\S+)(?<name>.*)");
    private static final String QUESTION_STRING = "question";

    /**
     * Parses the arguments provided by the client.
     * @param arguments The arguments provided.
     * @return The corresponding Command
     * @throws ParseException If the command format is invalid
     */
    public Command parse(String arguments) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(arguments.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, String.format(TEMPLATE,
                    LoadAttributeCommand.MESSAGE_USAGE, LoadQuestionCommand.MESSAGE_USAGE)));
        }

        final String dataType = matcher.group("data");
        final String sessionName = matcher.group("name");

        if (dataType.equals(ATTRIBUTE_STRING)) {
            return new LoadAttributeCommand(sessionName);
        } else if (dataType.equals(QUESTION_STRING)) {
            return new LoadQuestionCommand(sessionName);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, String.format(TEMPLATE,
                    LoadAttributeCommand.MESSAGE_USAGE, LoadQuestionCommand.MESSAGE_USAGE)));
        }
    }
}
