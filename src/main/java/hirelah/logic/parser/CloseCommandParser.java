package hirelah.logic.parser;

import hirelah.logic.commands.CloseReportCommand;
import hirelah.logic.commands.CloseSessionCommand;
import hirelah.logic.commands.Command;
import hirelah.logic.parser.exceptions.ParseException;

/**
 * Checks that the user typed "close session", then returns a {@code CloseSessionCommand} if successful.
 */
public class CloseCommandParser implements Parser<Command> {
    public static final String MESSAGE_FAILURE =
            "Invalid command!\nTo close the session, give the command: close session\n"
            + "To close a report, give the command: close report";

    @Override
    public Command parse(String userInput) throws ParseException {
        switch (userInput) {
        case "session":
            return new CloseSessionCommand();
        case "report":
            return new CloseReportCommand();
        default:
            throw new ParseException(MESSAGE_FAILURE);
        }
    }
}
