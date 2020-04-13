package hirelah.logic.parser;

import static hirelah.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hirelah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import hirelah.logic.commands.CloseReportCommand;
import hirelah.logic.commands.CloseSessionCommand;

class CloseCommandParserTest {
    private CloseCommandParser parser = new CloseCommandParser();

    @Test
    void parse_session_success() {
        assertParseSuccess(parser, "session", new CloseSessionCommand());
    }

    @Test
    void parse_report_success() {
        assertParseSuccess(parser, "report", new CloseReportCommand());
    }

    @Test
    void parse_blank_throwsException() {
        assertParseFailure(parser, "", CloseCommandParser.MESSAGE_FAILURE);
    }
}
