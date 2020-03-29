package seedu.address.ui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
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
    private BarChart<String, Double> attributeScores;

    @FXML
    private Button viewResume;

    @SuppressWarnings("unchecked")
    public DetailedIntervieweeCard(Interviewee interviewee) {
        super(FXML);
        this.interviewee = interviewee;
        name.setText("Full Name: " + interviewee.getFullName());
        id.setText("ID:         " + interviewee.getId());
        alias.setText("Alias:     " + interviewee.getAlias().orElse("No alias has been set."));
        ObservableList<XYChart.Data<String, Double>> data = convertMapToList(this.interviewee
                .getTranscript()
                .get()
                .getAttributeToScoreMapView());
        XYChart.Series<String, Double> attributeData = new XYChart.Series<>("Attributes", data);

        //setAll() method causes a Unchecked generics array creation for varargs parameter warning,
        // but it shouldn't cause errors
        attributeScores.getData().setAll(attributeData);
        attributeScores.setTitle("Attribute Scores");
        attributeScores.getXAxis().setAnimated(false);



        viewResume.setOnAction(en -> {
            File resumePath = interviewee.getResume()
                                            .orElse(new File("./src/main/resources/help/NoResume.pdf"));
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

        mapToScore.addListener((MapChangeListener<Attribute, Double>) change -> {
            if (change.wasAdded()) {
                String attributeAdded = change.getKey().toString();
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
