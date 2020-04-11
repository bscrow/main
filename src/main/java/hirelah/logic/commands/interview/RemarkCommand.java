package hirelah.logic.commands.interview;

import static hirelah.logic.util.CommandUtil.saveTranscript;

import hirelah.logic.commands.Command;
import hirelah.logic.commands.CommandResult;
import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.storage.Storage;

/**
 * Remark command is an Interview phase command that adds a Remark at the current interview time
 * to the Interviewee's Transcript.
 */
public class RemarkCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Added the remark.";

    private final String remark;

    public RemarkCommand(String remark) {
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        if (remark.equals("")) {
            throw new CommandException("Remark can not be blank.");
        }
        model.getCurrentTranscript().addRemark(this.remark);
        saveTranscript(model, storage);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
