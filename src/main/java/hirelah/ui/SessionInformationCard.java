package hirelah.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * An UI component that displays meta information of an Interview Session.
 */
public class SessionInformationCard extends UiPart<Region> {

    private static final String FXML = "SessionInformationCard.fxml";

    @FXML
    private Label sessionName;

    @FXML
    private Label interviews;

    @FXML
    private Label finaliseStatus;

    public SessionInformationCard() {
        super(FXML);
        sessionName.setText("Session: " + "CEO Interview");
        ObservableValue<Integer> numberInterviewed = new SimpleIntegerProperty(9).asObject();
        ObservableValue<Integer> numberInterviewees = new SimpleIntegerProperty(20).asObject();
        ObservableValue<Boolean> isFinalised = new SimpleBooleanProperty(true).asObject();

        interviews.textProperty().bind(
                Bindings.format("  - %d / %d interviewed", numberInterviewed, numberInterviewees)
        );
        finaliseStatus.textProperty().bind(
                Bindings.format(isFinalised.getValue() ? "  - Rubrics Finalised" : "  - Rubrics NOT Finalised")
        );

    }

}
