package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_INTEGRITY;
import static seedu.address.logic.commands.CommandTestUtility.VALID_COMMAND_ADD;
import static seedu.address.logic.commands.CommandTestUtility.VALID_COMMAND_DELETE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_COMMAND_EDIT;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEWEE_JANE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEWEE_JANICE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_PROPERTY_INTERVIEWEE;
import static seedu.address.logic.commands.CommandTestUtility.WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddIntervieweeCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteIntervieweeCommand;
import seedu.address.logic.commands.EditIntervieweeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class NormalParserTest {

    private NormalParser parser = new NormalParser();

    @Test
    void parse_validAddCommand_success() throws ParseException {
        Command result = parser.parseCommand(VALID_COMMAND_ADD + WHITESPACE
                + VALID_PROPERTY_INTERVIEWEE + WHITESPACE + VALID_INTERVIEWEE_JANE);
        assertEquals(result, new AddIntervieweeCommand("Jane Doe"));
    }

    @Test
    void parse_validEditCommand_success() throws ParseException {
        Command result = parser.parseCommand(VALID_COMMAND_EDIT + WHITESPACE
                + VALID_PROPERTY_INTERVIEWEE + WHITESPACE + PREFIX_OLD
                + WHITESPACE + VALID_INTERVIEWEE_JANE + WHITESPACE + PREFIX_NEW
                + WHITESPACE + VALID_INTERVIEWEE_JANICE + WHITESPACE);
        assertEquals(result, new EditIntervieweeCommand("Jane Doe", "Janice Doe"));
    }

    @Test
    void parse_validDeleteCommand_success() throws ParseException {
        Command result = parser.parseCommand(VALID_COMMAND_DELETE + WHITESPACE
                + VALID_PROPERTY_INTERVIEWEE + WHITESPACE + VALID_INTERVIEWEE_JANE);
        assertEquals(result, new DeleteIntervieweeCommand("Jane Doe"));
    }

    @Test
    void parse_invalidCommand_failure() {
        assertThrows(ParseException.class, () -> parser.parseCommand(VALID_ATTRIBUTE_INTEGRITY + WHITESPACE
                + VALID_PROPERTY_INTERVIEWEE + WHITESPACE
                + PREFIX_OLD + WHITESPACE + VALID_INTERVIEWEE_JANE
                + WHITESPACE + PREFIX_NEW
                + WHITESPACE + VALID_INTERVIEWEE_JANICE));
    }
}
