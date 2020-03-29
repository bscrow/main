package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.hirelah.Interviewee;

import java.io.File;

/**
 * An UI component that displays information of a {@code Interviewee}.
 */
public class IntervieweeCard extends UiPart<Region> {

    private static final String FXML = "IntervieweeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Interviewee interviewee;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label alias;
    @FXML
    private ImageView done;

    public IntervieweeCard(Interviewee interviewee) {
        super(FXML);
        this.interviewee = interviewee;
        name.setText("Full Name: " + interviewee.getFullName());
        id.setText("ID:         " + interviewee.getId());
        alias.setText("Alias:     " + interviewee.getAlias().orElse("No alias has been set."));
        if (interviewee.getTranscript().isPresent()) {
            File imgFile = new File("./src/main/resources/images/checkbox_tick.png");
            done.setImage(new Image(imgFile.toURI().toString()));
        } else {
            File imgFile = new File("./src/main/resources/images/checkbox_cross.png");
            done.setImage(new Image(imgFile.toURI().toString()));
        }
        done.setTranslateX(10);
        done.setTranslateY(25);
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
