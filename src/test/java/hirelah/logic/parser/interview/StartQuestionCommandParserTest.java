package hirelah.logic.parser.interview;

import static hirelah.logic.commands.CommandTestUtility.INVALID_QUESTION_NUMBER_1;
import static hirelah.logic.commands.CommandTestUtility.INVALID_QUESTION_NUMBER_2;
import static hirelah.logic.commands.CommandTestUtility.INVALID_QUESTION_NUMBER_3;
import static hirelah.logic.commands.CommandTestUtility.VALID_QUESTION_NUMBER_1;
import static hirelah.logic.commands.CommandTestUtility.VALID_QUESTION_NUMBER_14;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import hirelah.commons.core.Messages;
import hirelah.logic.commands.interview.StartQuestionCommand;

class StartQuestionCommandParserTest {
    private StartQuestionCommandParser parser = new StartQuestionCommandParser();

    @Test
    void parse_invalidQuestionFormat_exceptionThrown() {
        String exceptionMessage =
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, StartQuestionCommand.MESSAGE_USAGE);
        assertParseFailure(parser, INVALID_QUESTION_NUMBER_1, exceptionMessage);
        assertParseFailure(parser, INVALID_QUESTION_NUMBER_2, exceptionMessage);
        assertParseFailure(parser, INVALID_QUESTION_NUMBER_3, exceptionMessage);
    }

    @Test
    void parse_validInput_correctCommandReturned() {
        assertParseSuccess(parser, VALID_QUESTION_NUMBER_1, new StartQuestionCommand(1));
        assertParseSuccess(parser, VALID_QUESTION_NUMBER_14, new StartQuestionCommand(14));
    }
}
