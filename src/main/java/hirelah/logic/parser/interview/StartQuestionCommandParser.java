package hirelah.logic.parser.interview;

import static hirelah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hirelah.logic.commands.interview.StartQuestionCommand;
import hirelah.logic.parser.Parser;
import hirelah.logic.parser.ParserUtil;
import hirelah.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments of the form {@code q<question number>} and returns a StartQuestionCommand
 */
public class StartQuestionCommandParser implements Parser<StartQuestionCommand> {
    public static final Pattern QUESTION_FORMAT = Pattern.compile("q(?<questionNumber>\\d+)");

    @Override
    public StartQuestionCommand parse(String userInput) throws ParseException {
        Matcher matcher = QUESTION_FORMAT.matcher(userInput);
        if (matcher.matches()) {
            int index = ParserUtil.checkValidQuestionNumber(matcher.group("questionNumber"));
            return new StartQuestionCommand(index);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartQuestionCommand.MESSAGE_USAGE));
        }
    }
}
