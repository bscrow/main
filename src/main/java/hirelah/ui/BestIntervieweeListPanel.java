package hirelah.ui;

import java.util.logging.Logger;

import hirelah.commons.core.LogsCenter;
import hirelah.model.hirelah.IntervieweeToScore;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of best n interviewees ranked by a user-defined metric.
 */
public class BestIntervieweeListPanel extends UiPart<Region> {
    private static final String FXML = "IntervieweeListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(BestIntervieweeListPanel.class);

    @FXML
    private Label title;
    @FXML
    private ListView<IntervieweeToScore> intervieweeListView;

    public BestIntervieweeListPanel(ObservableList<IntervieweeToScore> intervieweeList) {
        super(FXML);
        title.setText("Best Interviewees");
        intervieweeListView.setItems(intervieweeList);
        intervieweeListView.setCellFactory(listView -> new IntervieweeToScoreListViewCell());
        intervieweeListView.getItems().addListener(
                (ListChangeListener<IntervieweeToScore>) c -> intervieweeListView.scrollTo(c.getList().size() - 1));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Interviewee} using a {@code IntervieweeCard}.
     */
    class IntervieweeToScoreListViewCell extends ListCell<IntervieweeToScore> {
        @Override
        protected void updateItem(IntervieweeToScore score, boolean empty) {
            super.updateItem(score, empty);

            if (empty || score == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new IntervieweeCard(score.getInterviewee(), score.getScore()).getRoot());
            }
        }
    }

}
