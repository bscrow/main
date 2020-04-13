package hirelah.logic.parser.interview;

import static hirelah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hirelah.logic.commands.interview.ScoreCommand;
import hirelah.logic.parser.Parser;
import hirelah.logic.parser.exceptions.ParseException;

/**
 * Parses input of the form {@code :set <attribute> <score>} and returns a ScoreCommand.
 */
public class ScoreCommandParser implements Parser<ScoreCommand> {
    public static final Pattern SCORE_FORMAT =
            Pattern.compile("(?<attribute>[\\p{Alpha}][\\p{Alpha} ]*?)\\s+(?<score>\\d+(\\.\\d+)?)");

    @Override
    public ScoreCommand parse(String userInput) throws ParseException {
        Matcher matcher = SCORE_FORMAT.matcher(userInput);
        if (matcher.matches()) {
            double score = Double.parseDouble(matcher.group("score"));
            return new ScoreCommand(matcher.group("attribute"), score);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScoreCommand.MESSAGE_USAGE));
        }
    }
}
