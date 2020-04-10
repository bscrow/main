package seedu.address.ui;


import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.hirelah.Attribute;
import seedu.address.model.hirelah.Interviewee;

import java.util.Map;

/**
 * An UI component that displays information of a {@code Interviewee}.
 */
public class DetailedIntervieweeCard extends UiPart<Region> {

    private static final String FXML = "DetailedIntervieweeCard.fxml";

    public final Interviewee interviewee;

    @FXML
    private VBox detailedIntervieweePane;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label alias;

    @FXML
    private Button viewResume;

    @FXML
    private TableView<Pair<Attribute, Double>> attributeToScoreTable;


    public DetailedIntervieweeCard(Interviewee interviewee, CommandExecutor commandExecutor) {
        super(FXML);
        this.interviewee = interviewee;
        ObservableList<Pair<Attribute, Double>> attributeToScoreData = interviewee.getTranscript()
                .get()
                .getAttributeToScoreData();

        name.setText(interviewee.getFullName());
        id.setText("ID:         " + interviewee.getId());
        alias.setText("Alias:     " + interviewee.getAlias().orElse("No alias has been set."));
        viewResume.setText(interviewee.getResume().isPresent() ? "View Resume" : "No Resume");
        viewResume.setOnAction(en -> {
            try {
                commandExecutor.execute("resume " + this.interviewee.getFullName());
            } catch (CommandException | IllegalValueException e) {
                e.printStackTrace();
            }
        });

        attributeToScoreTable.setItems(attributeToScoreData);

        TableColumn<Pair<Attribute, Double>, String> attributeColumn = new TableColumn<>("Attribute:");
        attributeColumn.setCellValueFactory(p ->
                new SimpleObjectProperty<>(truncateAttributeName(p.getValue().getKey().toString())));

        attributeToScoreTable.getColumns().set(0, attributeColumn);

        TableColumn<Pair<Attribute, Double>, String> scoreColumn = new TableColumn<>("Score:");
        scoreColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(setValueLabels(p.getValue().getValue())));

        attributeToScoreTable.getColumns().set(1, scoreColumn);

        attributeColumn.prefWidthProperty().bind(attributeToScoreTable.widthProperty().multiply(0.58));
        scoreColumn.prefWidthProperty().bind(attributeToScoreTable.widthProperty().multiply(0.31));

        attributeColumn.setResizable(false);
        scoreColumn.setResizable(false);
        attributeToScoreTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Truncates the input xTicks if it is longer than 20 characters.
     *
     * @param xTick String representing a tick on the X axis.
     *
     * @return String of the tick after truncation.
     */
    private String truncateAttributeName(String xTick) {
        if (xTick.length() > 14) {
            return xTick.substring(0, 12) + "...";
        } else {
            return xTick;
        }
    }

    private String setValueLabels(Double value) {
        return !Double.isNaN(value) ? String.format("%.3f", value) : "-";
    }
}
