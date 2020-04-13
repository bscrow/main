package hirelah.logic.parser;

import static hirelah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirelah.logic.commands.CommandTestUtility.VALID_PLURAL_ATTRIBUTE;
import static hirelah.logic.commands.CommandTestUtility.VALID_PLURAL_QUESTION;
import static hirelah.logic.commands.CommandTestUtility.VALID_PROPERTY_INTERVIEWEE;
import static hirelah.logic.commands.CommandTestUtility.VALID_PROPERTY_METRIC;
import static hirelah.logic.commands.CommandTestUtility.VALID_SESSION;
import static hirelah.logic.commands.CommandTestUtility.WHITESPACE;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import hirelah.logic.commands.LoadAttributeCommand;
import hirelah.logic.commands.LoadQuestionCommand;

public class LoadParserTest {
    private static final String EMPTY_STRING = "";
    private LoadParser parser = new LoadParser();

    @Test
    public void parse_noArgument_failure() {
        assertParseFailure(parser, WHITESPACE + VALID_PLURAL_ATTRIBUTE,
                LoadParser.MESSAGE_EMPTY_SESSION);
        assertParseFailure(parser, WHITESPACE + VALID_PLURAL_QUESTION,
                LoadParser.MESSAGE_EMPTY_SESSION);
    }

    @Test
    public void parse_completeArgument_success() {
        assertParseSuccess(parser, WHITESPACE + VALID_PLURAL_ATTRIBUTE + WHITESPACE + VALID_SESSION,
                new LoadAttributeCommand(VALID_SESSION));
        assertParseSuccess(parser, WHITESPACE + VALID_PLURAL_QUESTION + WHITESPACE + VALID_SESSION,
                new LoadQuestionCommand(VALID_SESSION));
    }

    @Test
    public void parse_unknownProperty_failure() {
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, String.format(LoadParser.TEMPLATE,
                LoadAttributeCommand.MESSAGE_USAGE, LoadQuestionCommand.MESSAGE_USAGE));

        assertParseFailure(parser, WHITESPACE + VALID_PROPERTY_METRIC + WHITESPACE + VALID_SESSION, message);
        assertParseFailure(parser, WHITESPACE + VALID_PROPERTY_INTERVIEWEE + WHITESPACE + VALID_SESSION,
                message);
    }
}
