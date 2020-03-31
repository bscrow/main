package seedu.address.logic.commands.interview;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Question;
import seedu.address.model.hirelah.QuestionList;
import seedu.address.model.hirelah.exceptions.IllegalActionException;

/**
 * Marks the beginning of the answer to a question in the Interview transcript.
 */
public class StartQuestionCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Marked the start of question %d";

    private final int questionNumber;

    public StartQuestionCommand(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        QuestionList questionList = model.getQuestionList();
        try {
            Question question = questionList.find(questionNumber);
            model.getCurrentTranscript().startQuestion(questionNumber, question);
        } catch (IllegalValueException | IllegalActionException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, questionNumber));
    }
}
