package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtility.INVALID_DUMMY_VALUE;
import static seedu.address.logic.commands.CommandTestUtility.INVALID_QUESTION_NUMBER_1;
import static seedu.address.logic.commands.CommandTestUtility.INVALID_QUESTION_NUMBER_2;
import static seedu.address.logic.commands.CommandTestUtility.INVALID_TIME_123;
import static seedu.address.logic.commands.CommandTestUtility.INVALID_TIME_1234;
import static seedu.address.logic.commands.CommandTestUtility.VALID_QUESTION_NUMBER_1;
import static seedu.address.logic.commands.CommandTestUtility.VALID_QUESTION_NUMBER_14;
import static seedu.address.logic.commands.CommandTestUtility.VALID_TIME_123;
import static seedu.address.logic.commands.CommandTestUtility.VALID_TIME_30;
import static seedu.address.logic.commands.CommandTestUtility.WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NavigationQuestionCommand;
import seedu.address.logic.commands.NavigationTimeCommand;

class NavigationCommandParserTest {

    private NavigationCommandParser parser = new NavigationCommandParser();

    @Test
    public void parse_allFieldsPresentNavigationQuestion_success() {

        assertParseSuccess(parser, WHITESPACE + VALID_QUESTION_NUMBER_14,
                new NavigationQuestionCommand(14));

        assertParseSuccess(parser, WHITESPACE + VALID_QUESTION_NUMBER_1,
                new NavigationQuestionCommand(1));

    }

    @Test
    public void parse_allFieldsPresentNavigationTime_success() {

        Duration thirtyMin = Duration.ofMinutes(30);
        assertParseSuccess(parser, WHITESPACE + VALID_TIME_30,
                new NavigationTimeCommand(thirtyMin));

        Duration hundredTwentyThreeMin = Duration.ofMinutes(123);
        Duration hundredTwentyThreeMinFortyFiveSec = hundredTwentyThreeMin.plusSeconds(45);
        assertParseSuccess(parser, WHITESPACE + VALID_TIME_123,
                new NavigationTimeCommand(hundredTwentyThreeMinFortyFiveSec));

    }

    @Test
    public void parse_invalidFieldsNavigationQuestion_failure() {

        assertParseFailure(parser, WHITESPACE + INVALID_QUESTION_NUMBER_1,
                MESSAGE_UNKNOWN_COMMAND);

        assertParseFailure(parser, WHITESPACE + INVALID_QUESTION_NUMBER_2,
                MESSAGE_UNKNOWN_COMMAND);

    }

    @Test
    public void parse_invalidFieldsNavigationTime_failure() {

        assertParseFailure(parser, WHITESPACE + INVALID_TIME_123,
                MESSAGE_UNKNOWN_COMMAND);

        assertParseFailure(parser, WHITESPACE + INVALID_TIME_1234,
                MESSAGE_UNKNOWN_COMMAND);

    }

    @Test
    void parse_compulsoryFieldsMissing_success() {

        assertParseFailure(parser, WHITESPACE + INVALID_DUMMY_VALUE,
                MESSAGE_UNKNOWN_COMMAND);

    }

    @Test
    public void parse_argumentMissing_failure() {
        assertParseFailure(parser, WHITESPACE, MESSAGE_UNKNOWN_COMMAND);
    }
}
