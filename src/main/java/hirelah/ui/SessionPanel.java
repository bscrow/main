package hirelah.ui;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import hirelah.commons.core.LogsCenter;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * Panel showing the sessions available.
 */
public class SessionPanel extends UiPart<Region> {
    private static final String FXML = "SessionPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SessionPanel.class);

    @FXML
    private ListView<File> sessionsList;

    /**
     * Constructs a SessionPanel which displays the information regarding a session.
     *
     * @param availableSessions List of File indicating the path to each session directory.
     */
    public SessionPanel(List<File> availableSessions) {
        super(FXML);
        sessionsList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(File sessionFile, boolean empty) {
                super.updateItem(sessionFile, empty);
                if (empty || sessionFile == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(sessionFile.toString());
                    setPrefWidth(150.0);
                    setWrapText(true);
                }
            }
        });

        sessionsList.setItems(FXCollections.observableList(availableSessions));
        AnchorPane.setTopAnchor(sessionsList, 50.0);
        AnchorPane.setRightAnchor(sessionsList, 100.0);
        AnchorPane.setBottomAnchor(sessionsList, 50.0);
        AnchorPane.setLeftAnchor(sessionsList, 100.0);


    }
}

