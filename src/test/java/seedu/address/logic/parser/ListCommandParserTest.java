package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtility.VALID_ALIAS_JANE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_PROPERTY_ATTRIBUTE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_PROPERTY_INTERVIEWEE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_PROPERTY_QUESTION;
import static seedu.address.logic.commands.CommandTestUtility.WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListAttributeCommand;
import seedu.address.logic.commands.ListIntervieweeCommand;
import seedu.address.logic.commands.ListQuestionCommand;

class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    void parse_validCommand_success() {

        assertParseSuccess(parser, WHITESPACE + VALID_PROPERTY_INTERVIEWEE,
                new ListIntervieweeCommand());

        assertParseSuccess(parser, WHITESPACE + VALID_PROPERTY_ATTRIBUTE,
                new ListAttributeCommand());

        assertParseSuccess(parser, WHITESPACE + VALID_PROPERTY_QUESTION,
                new ListQuestionCommand());
    }

    @Test
    void parse_invalidCommand_success() {

        assertParseFailure(parser, WHITESPACE + VALID_ALIAS_JANE,
                MESSAGE_UNKNOWN_COMMAND);
    }
}
