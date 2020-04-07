package seedu.address.ui;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * Panel showing the sessions available.
 */
public class SessionPanel extends UiPart<Region> {
    private static final String FXML = "SessionPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SessionPanel.class);

    /**
     * Constructs a SessionPanel which displays the information regarding a session.
     *
     * TODO: 6/4/2020  implement SessionPanel based on an ObservableList of Sessions once ready.
     */
    public SessionPanel() {
        super(FXML);
    }

}

