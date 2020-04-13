package hirelah.logic.parser;

import static hirelah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import hirelah.logic.commands.ExportReportCommand;
import hirelah.logic.parser.exceptions.ParseException;

/**
 * Parses the ExportReportCommand with the identifier.
 */
public class ExportReportCommandParser implements Parser<ExportReportCommand> {

    /**
     * Parses the given {@code String} of identifier in the context of the ExportReportCommand
     * and returns an ExportReportCommand object for execution.
     *
     * @param arguments the identifier of the Interviewee
     * @throws ParseException if the user input is not a valid command word
     */
    public ExportReportCommand parse(String arguments) throws ParseException {

        if (arguments.trim().equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportReportCommand.MESSAGE_USAGE));
        }
        return new ExportReportCommand(arguments.trim());
    }
}
