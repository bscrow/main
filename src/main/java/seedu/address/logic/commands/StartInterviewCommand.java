package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Interviewee;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

/**
 * Changes the app to the interview phase to interview the given interviewee. Fails if no interviewee can
 * be identified via the String given, or the interviewee is already interviewed.
 */
public class StartInterviewCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Interview with %s started!";

    private String identifier;

    public StartInterviewCommand(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            Interviewee interviewee = model.getIntervieweeList().getInterviewee(identifier);
            model.startInterview(interviewee);
            return new CommandResult(String.format(MESSAGE_SUCCESS, interviewee), ToggleView.TRANSCRIPT);
        } catch (IllegalActionException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
