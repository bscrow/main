package seedu.address.ui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.hirelah.Attribute;
import seedu.address.model.hirelah.Interviewee;

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
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Double> attributeScores;

    @FXML
    private Button viewResume;

    public DetailedIntervieweeCard(Interviewee interviewee) {
        super(FXML);
        this.interviewee = interviewee;
        name.setText(interviewee.getFullName());
        id.setText("ID:         " + interviewee.getId());
        alias.setText("Alias:     " + interviewee.getAlias().orElse("No alias has been set."));
        viewResume.setText(interviewee.getResume().isPresent() ? "View Resume" : "No Resume");

        initialiseChart();

        viewResume.setOnAction(en -> {
            if (interviewee.getResume().isEmpty()) {
                return;
            }
            File resumePath = interviewee.getResume().get();
            if (Desktop.isDesktopSupported()) {
                new Thread(() -> {
                    try {
                        Desktop.getDesktop().open(resumePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }

    /**
     * Initialises the BarChart for this card.
     */
    @SuppressWarnings("unchecked")
    private void initialiseChart() {
        ObservableList<XYChart.Data<String, Double>> data = convertMapToList(this.interviewee
                .getTranscript()
                .get()
                .getAttributeToScoreMapView());
        XYChart.Series<String, Double> attributeData = new XYChart.Series<>("Attributes", data);

        // setAll method should be safe in our usage but it raises an Unchecked varargs warning
        attributeScores.getData().setAll(attributeData);
        attributeScores.setLegendVisible(false);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
        yAxis.setTickUnit(25);
        yAxis.setLabel("Scores");

        xAxis.setLabel("Attributes");
        xAxis.setAnimated(false);
        xAxis.setMinWidth(data.size() * 30);
        xAxis.setMaxHeight(20);
        // xAxis.setTickLabelRotation(-10); Unnecessarrily flattens the BarChart

    }

    /**
     * Truncates the input xTicks if it is longer than 20 characters.
     *
     * @param xTick String representing a tick on the X axis.
     *
     * @return String of the tick after truncation
     */
    private String truncateTicks(String xTick) {
        if (xTick.length() > 20) {
            return xTick.substring(0, 17) + "...";
        } else {
            return xTick;
        }
    }


    /**
     * Converts the ObservableMap of Attribute to Score to an ObservableList of XYChart.Data of type String, Double,
     * used t0 plot a BarChart. A listener is added to the ObservableMap so that the change made by any put operation is
     * reflected in the BarChart.
     *
     * @param mapToScore ObservableMap of Attribute to Score.
     * @return ObservableList XYChart.Data String, Double  used as data input for BarChart.
     */
    private ObservableList<XYChart.Data<String, Double>> convertMapToList(
            ObservableMap<Attribute, Double> mapToScore) {

        ObservableList<XYChart.Data<String, Double>> attributeList = FXCollections.observableArrayList();

        for (Map.Entry<Attribute, Double> entry : mapToScore.entrySet()) {
            attributeList.add(new Data<>(truncateTicks(entry.getKey().toString()), entry.getValue()));
        }

        mapToScore.addListener((MapChangeListener<Attribute, Double>) change -> {
            if (change.wasAdded()) {
                String attributeAdded = truncateTicks(change.getKey().toString());
                System.out.println(attributeAdded);
                for (int i = 0; i < attributeList.size(); i++) {
                    if (attributeList.get(i).getXValue().equals(attributeAdded)) {
                        attributeList.remove(i);
                        break;
                    }
                }
                attributeList.add(new Data<>(attributeAdded, change.getValueAdded()));
            }
        });

        return attributeList;
    }

}
