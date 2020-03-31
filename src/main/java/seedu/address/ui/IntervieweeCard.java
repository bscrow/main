package seedu.address.ui;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.hirelah.Interviewee;

/**
 * An UI component that displays information of a {@code Interviewee}.
 */
public class IntervieweeCard extends UiPart<Region> {

    private static final String FXML = "IntervieweeListCard.fxml";
    private static final File CHECKBOX_TICK = new File("./src/main/resources/images/checkbox_tick.png");
    private static final File CHECKBOX_EMPTY = new File("./src/main/resources/images/checkbox_empty.png");


    public final Interviewee interviewee;

    private CommandExecutor commandExecutor;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label alias;
    @FXML
    private ImageView interviewStatus;
    @FXML
    private ImageView resumeStatus;

    public IntervieweeCard(Interviewee interviewee, CommandExecutor commandExecutor) {
        super(FXML);
        this.interviewee = interviewee;
        this.commandExecutor = commandExecutor;
        name.setText(interviewee.getFullName());
        id.setText("ID:         " + interviewee.getId());
        alias.setText("Alias:     " + interviewee.getAlias().orElse("No alias has been set."));
        if (interviewee.getTranscript().isPresent()) {
            interviewStatus.setImage(new Image(CHECKBOX_TICK.toURI().toString()));
        } else {
            interviewStatus.setImage(new Image(CHECKBOX_EMPTY.toURI().toString()));
        }
        if (interviewee.getResume().isPresent()) {
            resumeStatus.setImage(new Image(CHECKBOX_TICK.toURI().toString()));
        } else {
            resumeStatus.setImage(new Image(CHECKBOX_EMPTY.toURI().toString()));
        }
        this.getRoot().setOnKeyPressed(key -> {
            KeyCode keyCode = key.getCode();
            if (keyCode == KeyCode.ENTER) {
                try {
                    commandExecutor.execute("open " + this.interviewee.getFullName());
                } catch (CommandException | IllegalValueException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleOpen() {

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IntervieweeCard)) {
            return false;
        }

        // state check
        IntervieweeCard card = (IntervieweeCard) other;
        return id.getText().equals(card.id.getText())
                && interviewee.equals(card.interviewee);
    }
}
