package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Transcript;

/**
 * NavigationTimeCommand describes the behavior when the
 * client wants to navigate to a certain remark of the interview.
 */
public class NavigationTimeCommand extends Command {
    public static final String COMMAND_WORD = "to";
    public static final String MESSAGE_NAVIGATION_TIME_SUCCESS = "Here is the remark at time %d.%d: ";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Navigate to a particular time of an interviewee's interview.\n"
            + "Parameters: minutes.seconds of the interview\n"
            + "Example:  " + COMMAND_WORD + " 30.00";

    private final Duration timeQuery;

    public NavigationTimeCommand(Duration timeQuery) {
        this.timeQuery = timeQuery;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasCurrentInterviewee()) {
            throw new CommandException("You need to open a transcript of a particular interviewee "
                    + "to go to the answer of a question.");
        }
        Optional<Transcript> transcriptOfCurrentInterviewee = model.getCurrentInterviewee().getTranscript();
        return transcriptOfCurrentInterviewee
                .map(x -> x.getIndexAtTime(timeQuery))
                .map(x -> new NavigationCommandResult(String.format(MESSAGE_NAVIGATION_TIME_SUCCESS,
                        timeQuery.toMinutes(), timeQuery.toSeconds()), ToggleView.TRANSCRIPT, x))
                .orElseThrow(() -> new CommandException(
                        String.format("Interviewee %1$s has not been interviewed.",
                                model.getCurrentInterviewee().getFullName())));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NavigationTimeCommand
                && timeQuery.equals(((NavigationTimeCommand) other).timeQuery));
    }
}
