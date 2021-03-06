package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * StartSessionCommand start and create a new Session
 */
public class EndSessionCommand extends Command {
    public static final String COMMAND_WORD = "end session";
    public static final String MESSAGE_SUCCESS = "Successfully ended the Session";

    //Incomplete class
    //Will update the Command once we confirmed how we going to change the different sessions.

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(COMMAND_WORD);
    }
}
