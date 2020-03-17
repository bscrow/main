package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.hirelah.Remark;

/**
 * An UI component that displays information of an {@code Interviewee}.
 */
public class RemarkCard extends UiPart<Region> {

    private static final String FXML = "TranscriptListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Remark remark;

    @FXML
    private HBox cardPane;
    @FXML
    private Label timestamp;
    @FXML
    private Label content;
    @FXML
    private FlowPane questionTags;

    /**
     * Constructs a remark card based on the data stored in a Remark object. Implementation may change?
     *
     * @param remark remark object to take information from.
     */
    public RemarkCard(Remark remark) {
        super(FXML);
        this.remark = remark;

    }

}
