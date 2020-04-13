package hirelah.storage.serialisetests;

import static hirelah.commons.util.JsonUtil.readJsonFile;
import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalMetricList.getMetricList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.commons.util.JsonUtil;
import hirelah.model.hirelah.MetricList;
import hirelah.storage.JsonSerializableMetric;

public class JsonSerializableMetricTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMetricTest");
    private static final Path TYPICAL_METRIC_FILE = TEST_DATA_FOLDER.resolve("ValidMetric.json");
    private static final Path INVALID_METRIC_FILE = TEST_DATA_FOLDER.resolve("InvalidMetric.json");
    private static final Path DUPLICATE_METRIC_FILE = TEST_DATA_FOLDER.resolve("DuplicateMetric.json");

    @Test
    public void toModelType_validMetricFile_success() throws Exception {
        JsonSerializableMetric dataFromFile = readJsonFile(TYPICAL_METRIC_FILE ,
                JsonSerializableMetric.class).get();
        MetricList metricListFromFile = dataFromFile.toModelType();
        assertEquals(getMetricList(), metricListFromFile);
    }

    @Test
    public void toModelType_invalidMetricFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMetric dataFromFile = JsonUtil.readJsonFile(INVALID_METRIC_FILE,
                JsonSerializableMetric.class).get();
        assertThrows(IllegalValueException.class, () -> {
            dataFromFile.toModelType();
        });
    }

    @Test
    public void toModelType_duplicateMetricFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMetric dataFromFile = JsonUtil.readJsonFile(DUPLICATE_METRIC_FILE,
                JsonSerializableMetric.class).get();
        assertThrows(IllegalValueException.class, () -> {
            dataFromFile.toModelType();
        });
    }
}
