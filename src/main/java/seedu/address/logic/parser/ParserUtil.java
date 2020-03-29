package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    public static final String MESSAGE_NO_ARGUMENTS_BEFORE_PREFIX = "There is no arguments before prefix";

    private static final String PREFIX = "-";

    /** Parses the arguments to obtain substring that is before any prefix.
     *
     * @param arguments the arguments to be parsed
     * @return arguments parsed before prefix
     * @throws ParseException
     */
    public static String parseArgumentsBeforePrefix (String arguments) throws ParseException {
        String trimmedArguments = arguments.trim();
        String argumentsBeforePrefix = trimmedArguments.substring(0, trimmedArguments.indexOf(PREFIX)).trim();
        if (argumentsBeforePrefix.equals("")) {
            throw new ParseException(MESSAGE_NO_ARGUMENTS_BEFORE_PREFIX);
        }
        return argumentsBeforePrefix;
    }

    /** Checks whether the argument has any content.
     *
     * @param arguments the arguments to be checked
     * @return boolean whether the argument has any content
     */
    public static boolean isEmptyArgument (String arguments) {
        return (arguments.trim().equals(""));
    }

    /** Raises exception when the argument has no content.
     *
     * @param messageUsage the message to be checked
     * @throws ParseException if there is no content
     */
    public static void checkEmptyArgument (String messageUsage) throws ParseException {
        if (ParserUtil.isEmptyArgument(messageUsage)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage)
            );
        }
    }

    /** Parses the arguments to obtain substring that is after the first prefix.
     *
     * @param arguments the arguments to be parsed
     * @return arguments parsed after prefix
     * @throws ParseException
     */
    public static String parseArgumentsAfterPrefix (String arguments) throws ParseException {
        String trimmedArguments = arguments.trim();
        String argumentsBeforePrefix = trimmedArguments.substring(0, trimmedArguments.indexOf(PREFIX)).trim();
        if (argumentsBeforePrefix.equals("")) {
            throw new ParseException(MESSAGE_NO_ARGUMENTS_BEFORE_PREFIX);
        }
        return argumentsBeforePrefix;
    }
}
