package seedu.address.logic.commands;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Interviewee;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

/**
 * Opens the resume of the given interviewee, using the System default PDF viewer.
 */
public class OpenResumeCommand extends Command {
    public static final String COMMAND_WORD = "resume";
    public static final String MESSAGE_SUCCESS = "Opening resume..";
    public static final String MESSAGE_NO_RESUME =
            "Cannot find a resume! Please upload a resume via the upload command";

    private String identifier;

    public OpenResumeCommand(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Interviewee interviewee;
        try {
            interviewee = model.getIntervieweeList().getInterviewee(identifier);
        } catch (IllegalActionException e) {
            throw new CommandException(e.getMessage());
        }

        File resume = interviewee.getResume().orElseThrow(() -> new CommandException(MESSAGE_NO_RESUME));
        if (!resume.exists()) {
            throw new CommandException(MESSAGE_NO_RESUME);
        }
        openFile(resume);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Opens a file that exists.
     *
     * @param resume a file that is guaranteed to exist.
     * @throws CommandException if Desktop is not supported on the platform the app is running on.
     */
    private void openFile(File resume) throws CommandException {
        if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(resume);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            throw new CommandException("Cannot open pdf files!");
        }
    }
}
