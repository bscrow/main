package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
/**
 * Panel showing the sessions available.
 */
public class SessionPanel extends UiPart<Region> {
    private static final String FXML = "SessionPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SessionPanel.class);

    /**
     * Constructs a SessionPanel which displays the information regarding a session.
     */
    public SessionPanel() {
        super(FXML);
    }

}

