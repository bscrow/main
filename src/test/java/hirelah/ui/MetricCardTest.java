package hirelah.ui;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.control.TableViewMatchers;

import hirelah.model.hirelah.Metric;
import hirelah.testutil.TypicalMetricList;

import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class MetricCardTest {
    private static final String METRIC_NAME_ID = "#metricName";
    private static final String TABLE_ID = "#attributeToWeight";
    private static final Metric TEST_METRIC = TypicalMetricList.getMetricList().getObservableList().get(0);

    @AfterEach
    public void pause(FxRobot robot) {
        robot.sleep(500);
    }

    @Stop
    void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Initialise MetricCard test with a stage.
     *
     * @param stage Stage used to test.
     */
    @Start
    public void start(Stage stage) {
        Scene scene = new Scene(new MetricCard(TEST_METRIC).getRoot());
        scene.getStylesheets().add("/view/DarkTheme.css");
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void metricNameIsDisplayedCorrectly() {
        verifyThat(METRIC_NAME_ID, hasText(TEST_METRIC.getName()));
    }

    @Test
    public void metricTableDisplayedCorrectly() {
        verifyThat(TABLE_ID, TableViewMatchers.hasNumRows(TEST_METRIC.getMap().size()));
    }

}
