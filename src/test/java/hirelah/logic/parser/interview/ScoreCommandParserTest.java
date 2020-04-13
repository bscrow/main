package hirelah.logic.parser.interview;

import static hirelah.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_INTEGRITY;
import static hirelah.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_PERSISTENCE;
import static hirelah.logic.commands.CommandTestUtility.VALID_DOUBLE;
import static hirelah.logic.commands.CommandTestUtility.WHITESPACE;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import hirelah.commons.core.Messages;
import hirelah.logic.commands.interview.ScoreCommand;

class ScoreCommandParserTest {
    private ScoreCommandParser parser = new ScoreCommandParser();

    @Test
    void parse_blankAttribute_exceptionThrown() {
        assertParseFailure(parser, " 4.23",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidNumber_exceptionThrown() {
        assertParseFailure(parser, VALID_ATTRIBUTE_INTEGRITY + WHITESPACE + "44f",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_validInput_correctCommandReturned() {
        assertParseSuccess(parser, VALID_ATTRIBUTE_PERSISTENCE + WHITESPACE + VALID_DOUBLE,
                new ScoreCommand(VALID_ATTRIBUTE_PERSISTENCE, VALID_DOUBLE));
    }
}
