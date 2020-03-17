package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEWEE_JANE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEWEE_JANICE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_PROPERTY_INTERVIEWEE;
import static seedu.address.logic.commands.CommandTestUtility.WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditIntervieweeCommand;

class EditIntervieweeCommandParserTest {

    private EditIntervieweeCommandParser parser = new EditIntervieweeCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, WHITESPACE + VALID_PROPERTY_INTERVIEWEE
                        + WHITESPACE + PREFIX_OLD + WHITESPACE + VALID_INTERVIEWEE_JANE
                        + WHITESPACE + PREFIX_NEW + WHITESPACE + VALID_INTERVIEWEE_JANICE
                        + WHITESPACE,
                new EditIntervieweeCommand("Jane Doe", "Janice Doe"));
    }

    @Test
    void parse_oldFieldMissing_failure() {
        assertParseFailure(parser, WHITESPACE + VALID_PROPERTY_INTERVIEWEE
                        + WHITESPACE + PREFIX_OLD
                        + WHITESPACE + PREFIX_NEW
                        + WHITESPACE + VALID_INTERVIEWEE_JANICE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditIntervieweeCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_newFieldMissing_failure() {
        assertParseFailure(parser, WHITESPACE + VALID_PROPERTY_INTERVIEWEE
                        + WHITESPACE + PREFIX_OLD
                        + WHITESPACE + VALID_INTERVIEWEE_JANE
                        + WHITESPACE + PREFIX_NEW,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditIntervieweeCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_oldPrefixMissing_failure() {
        assertParseFailure(parser, WHITESPACE + VALID_PROPERTY_INTERVIEWEE
                        + WHITESPACE + VALID_INTERVIEWEE_JANE
                        + WHITESPACE + PREFIX_NEW,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditIntervieweeCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_newPrefixMissing_failure() {
        assertParseFailure(parser, WHITESPACE + VALID_PROPERTY_INTERVIEWEE
                        + WHITESPACE + PREFIX_OLD
                        + WHITESPACE + VALID_INTERVIEWEE_JANICE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditIntervieweeCommand.MESSAGE_USAGE));
    }
}
