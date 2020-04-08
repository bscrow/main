package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.hirelah.Attribute;
import seedu.address.model.hirelah.Interviewee;

/**
 * An UI component that displays information of a {@code Interviewee}.
 */
public class DetailedIntervieweeCard extends UiPart<Region> {

    private static final String FXML = "DetailedIntervieweeCard.fxml";

    public final Interviewee interviewee;
    private final ObservableList<Data<Double, String>> data;
    //private final ObservableList<Data<Number, String>> dataLabels;

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
    private StackPane attributeScore;

    @FXML
    private CategoryAxis yAxis;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private BarChart<Double, String> scoreChart;

    @FXML
    private BarChart<Number, String> chartLabel;

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

        //dataLabels = convertPairToDataLabels(attributeToScoreData);
        data = convertPairToData(attributeToScoreData);
        setListChangeListener(attributeToScoreData);

        initialiseScoreChart();
        //initialiseChartLabel();

        viewResume.setOnAction(en -> {
            try {
                commandExecutor.execute("resume " + this.interviewee.getFullName());
            } catch (CommandException | IllegalValueException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Initialises the BarChart for this card.
     */
    @SuppressWarnings("unchecked")
    private void initialiseScoreChart() {
        XYChart.Series<Double, String> attributeSeries = new XYChart.Series<>("Scores", data);
        scoreChart.getData().setAll(attributeSeries);
        scoreChart.setLegendVisible(false);
        scoreChart.setTitle("Scores");
        scoreChart.setHorizontalGridLinesVisible(false);
        scoreChart.setVerticalGridLinesVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(10);
        xAxis.setTickUnit(2);
        xAxis.setMinWidth(80);
    }

    //@SuppressWarnings("unchecked")
    //private void initialiseChartLabel() {
    //    XYChart.Series<Number, String> labelSeries = new XYChart.Series<>("Scores", dataLabels);
    //    chartLabel.getData().setAll(labelSeries);
    //    chartLabel.getXAxis().setMinWidth(80);
    //    chartLabel.getXAxis().setTickLabelsVisible(false);
    //    chartLabel.getXAxis().setTickMarkVisible(false);
    //
    //    chartLabel.getYAxis().setTickLabelsVisible(true);
    //    chartLabel.getYAxis().setTickMarkVisible(false);
    //    chartLabel.getYAxis().translateXProperty().bind(
    //            chartLabel.getXAxis().widthProperty().multiply(2.0/3)
    //    );
    //    chartLabel.toFront();
    //    chartLabel.setVerticalGridLinesVisible(false);
    //    chartLabel.setHorizontalGridLinesVisible(false);
    //    chartLabel.setLegendVisible(false);
    //}

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
        return Double.isNaN(value) ? String.format("%.3f", value) : "-";
    }

    //private ObservableList<Data<Number, String>> convertPairToDataLabels(
    // ObservableList<Pair<Attribute, Double>> attributeToScoreData) {
    //    ObservableList<Data<Number, String>> attributeList = FXCollections.observableArrayList();
    //    for (Pair<Attribute, Double> attributeDoublePair : attributeToScoreData) {
    //        attributeList.add(new Data<>(0, setValueLabels(attributeDoublePair.getValue())));
    //    }
    //    return attributeList;
    //}

    /**
     * Converts the {@code ObservableList} containing {@code Pair} of {@code Attribute} to a {@code Double} score to
     * an ObservableList of XYChart.Data of type Double to String, used to plot a {@code BarChart}. A listener is
     * added to the input ObservableList so that any change made to it is reflected in the BarChart.
     *
     * @param attributeToScoreData ObservableList of Attribute to Score Pairs.
     * @return {@code ObservableList<XYChart.Data<String, Double>>}  used as data input for BarChart.
     */
    private ObservableList<Data<Double, String>> convertPairToData(
            ObservableList<Pair<Attribute, Double>> attributeToScoreData) {
        ObservableList<Data<Double, String>> attributeList = FXCollections.observableArrayList();
        for (Pair<Attribute, Double> attributeDoublePair : attributeToScoreData) {
            attributeList.add(new Data<>(attributeDoublePair.getValue(),
                    truncateAttributeName(attributeDoublePair.getKey().toString())));
        }
        return attributeList;
    }

    private void setListChangeListener(ObservableList<Pair<Attribute, Double>> attributeToScoreData) {

        attributeToScoreData.addListener((ListChangeListener<Pair<Attribute, Double>>) change -> {
            while (change.next()) {
                if (change.wasReplaced()) {
                    Pair<Attribute, Double> updatedPair = change.getRemoved().get(0);

                    // Check that the same Attribute is being added and replaced, which should be the case as
                    // Updates are made via set(index, Pair) method.
                    assert updatedPair.getKey().equals(change.getAddedSubList().get(0).getKey());

                    String attributeUpdated = truncateAttributeName(updatedPair.getKey().toString());
                    Double oldScore = updatedPair.getValue();
                    Double newScore = change.getAddedSubList().get(0).getValue();
                    Data<Double, String> newData = new Data<>(newScore, attributeUpdated);
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getYValue().equals(attributeUpdated)
                                && data.get(i).getXValue().equals(oldScore)) {
                            data.set(i, newData);
                            //dataLabels.set(i, new Data<>((Number) 0, setValueLabels(newScore)));
                        }
                    }
                }
            }
        });
    }

}
