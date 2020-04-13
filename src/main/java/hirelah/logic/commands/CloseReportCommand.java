package hirelah.logic.commands;

import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.storage.Storage;

/**
 * Closes the currently open report.
 */
public class CloseReportCommand extends Command {
    public static final String MESSAGE_SUCCESS = "closed the report of interviewee: %s";
    public static final String COMMAND_WORD = "report";

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        if (!model.hasCurrentInterviewee()) {
            throw new CommandException("No report currently open!");
        }
        return new ToggleCommandResult(String.format(MESSAGE_SUCCESS, model.getCurrentInterviewee()),
                ToggleView.CLOSE_TRANSCRIPT);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CloseReportCommand;
    }
}
