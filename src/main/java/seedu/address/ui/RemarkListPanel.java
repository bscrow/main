package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.hirelah.Interviewee;
import seedu.address.model.hirelah.Remark;

/**
 * Panel containing the list of remarks for an interviewee.
 */
public class RemarkListPanel extends UiPart<Region> {
    private static final String FXML = "RemarkCardListView.fxml";
    private final Logger logger = LogsCenter.getLogger(RemarkListPanel.class);

    private ObservableList<Remark> remarkList;

    @FXML
    private ListView<Remark> remarkCardListView;


    public RemarkListPanel(Interviewee interviewee) {
        super(FXML);
        if (interviewee.getTranscript().isPresent()) {
            remarkList = interviewee.getTranscript().get().getRemarkListView();
        } else {
            remarkList = null;
        }
        remarkCardListView.setItems(remarkList);
        remarkCardListView.setCellFactory(listView -> new RemarkListViewCell());
        remarkCardListView.getItems().addListener(
                (ListChangeListener<Remark>) c -> remarkCardListView.scrollTo(c.getList().size() - 1));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Remark} using a {@code RemarkCard}.
     */
    class RemarkListViewCell extends ListCell<Remark> {
        @Override
        protected void updateItem(Remark remark, boolean empty) {
            super.updateItem(remark, empty);

            if (empty || remark == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RemarkCard(remark).getRoot());
            }
        }
    }
}
