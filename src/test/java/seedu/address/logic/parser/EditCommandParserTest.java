package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_INTEGRITY;
import static seedu.address.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_PERSISTENCE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEWEE_JANE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEWEE_JANICE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_PROPERTY_ATTRIBUTE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_PROPERTY_INTERVIEWEE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_PROPERTY_QUESTION;
import static seedu.address.logic.commands.CommandTestUtility.VALID_QUESTION_WHAT;
import static seedu.address.logic.commands.CommandTestUtility.WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditAttributeCommand;
import seedu.address.logic.commands.EditIntervieweeCommand;
import seedu.address.logic.commands.EditQuestionCommand;

class EditCommandParserTest {
    private EditCommandParser parser = new EditCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, WHITESPACE + VALID_PROPERTY_INTERVIEWEE
                        + WHITESPACE + PREFIX_OLD + WHITESPACE + VALID_INTERVIEWEE_JANE
                        + WHITESPACE + PREFIX_NEW + WHITESPACE + VALID_INTERVIEWEE_JANICE,
                new EditIntervieweeCommand("Jane Doe", "Janice Doe"));

        assertParseSuccess(parser, WHITESPACE + VALID_PROPERTY_ATTRIBUTE
                        + WHITESPACE + PREFIX_OLD + WHITESPACE + VALID_ATTRIBUTE_PERSISTENCE
                        + WHITESPACE + PREFIX_NEW + WHITESPACE + VALID_ATTRIBUTE_INTEGRITY,
                new EditAttributeCommand("persistence", "integrity"));

        assertParseSuccess(parser, WHITESPACE + VALID_PROPERTY_QUESTION
                        + WHITESPACE + "1"
                        + WHITESPACE + VALID_QUESTION_WHAT,
                new EditQuestionCommand("1", "What is this question?"));
    }

    @Test
    void parse_compulsoryFieldsMissing_success() {

        assertParseFailure(parser, WHITESPACE + VALID_PROPERTY_INTERVIEWEE + WHITESPACE,
                MESSAGE_UNKNOWN_COMMAND);

        assertParseFailure(parser, WHITESPACE + VALID_PROPERTY_ATTRIBUTE + WHITESPACE,
                MESSAGE_UNKNOWN_COMMAND);

        assertParseFailure(parser, WHITESPACE + VALID_PROPERTY_QUESTION + WHITESPACE,
                MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void parse_argumentMissing_failure() {
        assertParseFailure(parser, WHITESPACE, MESSAGE_UNKNOWN_COMMAND);
    }
}
