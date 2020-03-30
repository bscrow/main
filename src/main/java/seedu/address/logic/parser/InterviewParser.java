package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.interview.EndCommand;
import seedu.address.logic.commands.interview.RemarkCommand;
import seedu.address.logic.commands.interview.ScoreCommand;
import seedu.address.logic.commands.interview.StartQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
    private static final Pattern SCORE_COMMAND_FORMAT =
            Pattern.compile(":(?<attribute>[\\p{Alpha}][\\p{Alpha} ]*?)\\s+(?<score>\\d+(\\.\\d+)?)");
    private static final Pattern START_QUESTION_COMMAND_FORMAT =
            Pattern.compile(":start q(?<questionNumber>\\d+)");

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
        } else {
            return new RemarkCommand(userInput);
        }
    }

    /**
     * Parses non-remark commands during the interview.
     *
     * @param userInput the user input.
     * @return the parsed command.
     * @throws ParseException if the command word cannot be identified with any command or attribute to score.
     */
    private Command parseSpecialCommand(String userInput) throws ParseException {
        if (userInput.equals(":end")) {
            return new EndCommand();
        }
        Matcher startQuestionMatcher = START_QUESTION_COMMAND_FORMAT.matcher(userInput.trim());
        if (startQuestionMatcher.matches()) {
            int questionNumber = Integer.parseInt(startQuestionMatcher.group("questionNumber"));
            return new StartQuestionCommand(questionNumber);
        }
        Matcher scoreMatcher = SCORE_COMMAND_FORMAT.matcher(userInput.trim());
        if (scoreMatcher.matches()) {
            double score = Double.parseDouble(scoreMatcher.group("score"));
            return new ScoreCommand(scoreMatcher.group("attribute"), score);
        }

        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }
}
