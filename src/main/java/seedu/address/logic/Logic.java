package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.hirelah.Attribute;
import seedu.address.model.hirelah.Interviewee;
import seedu.address.model.hirelah.Question;
import seedu.address.model.hirelah.Transcript;
/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the list of attributes */
    ObservableList<Attribute> getAttributeListView();

    /** Returns an unmodifiable view of the filtered list of interviewees */
    ObservableList<Interviewee> getFilteredIntervieweeListView();

    /** Returns an unmodifiable view of the list of questions */
    ObservableList<Question> getQuestionListView();

    /** Returns an unmodifiable view of the transcripts entries of an interviewee.
     *
     * @param interviewee The interviewee whose transcript we are interested in
     */
    ObservableList<Transcript> getTranscriptListView(Interviewee interviewee);



    /**
     * Returns the user prefs' sessions directory.
     */
    Path getSessionsDirectory();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
