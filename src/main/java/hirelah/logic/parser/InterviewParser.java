package hirelah.logic.parser;

import static hirelah.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hirelah.logic.commands.Command;
import hirelah.logic.commands.ListAttributeCommand;
import hirelah.logic.commands.ListMetricCommand;
import hirelah.logic.commands.ListQuestionCommand;
import hirelah.logic.commands.OpenResumeCommand;
import hirelah.logic.commands.interview.EndCommand;
import hirelah.logic.commands.interview.RemarkCommand;
import hirelah.logic.parser.exceptions.ParseException;
import hirelah.logic.parser.interview.ScoreCommandParser;
import hirelah.logic.parser.interview.StartQuestionCommandParser;

/*
 * InterviewParser
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 13 Mar 2020
 *
 */

/**
 * InterviewParser parses the input entered by the client
 * when HireLah! is not in interviewing mode.
 */
public class InterviewParser {
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        if (userInput.startsWith(":")) {
            return parseSpecialCommand(userInput);
        }
        return new RemarkCommand(userInput);
    }

    /**
     * Parses non-remark commands during the interview.
     *
     * @param userInput the user input.
     * @return the parsed command.
     * @throws ParseException if the command word cannot be identified with any command or attribute to score.
     */
    private Command parseSpecialCommand(String userInput) throws ParseException {
        switch (userInput) {
        case ":end":
            return new EndCommand();
        case ":attributes":
            return new ListAttributeCommand();
        case ":questions":
            return new ListQuestionCommand();
        case ":metrics":
            return new ListMetricCommand();
        case ":resume":
            return new OpenResumeCommand();
        default:
            break;
        }
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput);
        if (matcher.matches()) {
            String args = matcher.group("arguments").trim();
            switch (matcher.group("commandWord")) {
            case ":set":
                return new ScoreCommandParser().parse(args);
            case ":start":
                return new StartQuestionCommandParser().parse(args);
            default:
                break;
            }
        }

        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }
}
