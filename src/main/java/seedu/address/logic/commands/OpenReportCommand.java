package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Interviewee;
import seedu.address.model.hirelah.IntervieweeList;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

/**
 * OpenReportCommand describes the behavior when the
 * client wants to open an interview report of an interviewee.
 */
public class OpenReportCommand extends Command {
    public static final String COMMAND_WORD = "open";
    public static final String MESSAGE_SUCCESS = "Interview report opened: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the interview report of an interviewee."
            + "Example: " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_OPEN_REPORT_INTERVIEWEE_SUCCESS = "Successfully opened Interviewee report: %1$s";

    private final String identifier;

    /**
     * Creates an OpenReportCommand to open
     * the report of an {@code Interviewee}.
     */
    public OpenReportCommand(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        IntervieweeList interviewees = model.getIntervieweeList();
        try {
            Interviewee identifiedInterviewee = interviewees.getInterviewee(identifier);
            identifiedInterviewee
                    .getTranscript()
                    .orElseThrow(() -> new CommandException(
                            String.format("Interviewee %1$s has not been interviewed.", identifier)));
        } catch (IllegalActionException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(String.format(MESSAGE_OPEN_REPORT_INTERVIEWEE_SUCCESS, identifier),
                ToggleView.TRANSCRIPT));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenReportCommand // handles null
                && identifier.equals(((OpenReportCommand) other).identifier)); // state check
    }
}
