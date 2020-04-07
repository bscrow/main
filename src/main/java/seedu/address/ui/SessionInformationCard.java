package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.hirelah.Attribute;
import seedu.address.model.hirelah.Interviewee;

import java.util.Map;

/**
 * An UI component that displays information of a {@code Interviewee}.
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
