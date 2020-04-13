package hirelah.storage.storagetests;

import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalAttributes.getAttributePrefix;
import static hirelah.testutil.TypicalAttributes.getTypicalAttributes;
import static hirelah.testutil.TypicalMetricList.getMetricList;
import static hirelah.testutil.TypicalMetricList.getSamplemetricWeight;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import hirelah.commons.exceptions.DataConversionException;
import hirelah.model.hirelah.MetricList;
import hirelah.storage.MetricStorage;

public class MetricStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "MetricStorageTest");
    @TempDir
    public Path testFolder;

    @Test
    public void readMetric_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMetric(null));
    }

    private java.util.Optional<MetricList> readMetric(String filePath) throws Exception {
        MetricStorage metricStorage = new MetricStorage(Paths.get(filePath));
        return metricStorage.readMetric(addToTestDataPathIfNotNull(filePath));

    }
    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }
    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMetric("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readMetric("notJsonFormat.json"));
    }

    @Test
    public void readMetric_invalidMetricList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMetric("invalidMetric.json"));
    }

    @Test
    public void readMetric_invalidAndValidMetric_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMetric("invalidAndValidMetric.json"));
    }
    @Test
    public void readAndSaveMetrics_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMetricList.json");
        MetricList original = getMetricList();
        MetricStorage metricStorage = new MetricStorage(filePath);

        // Save in new file and read back
        metricStorage.saveMetrics(original);
        MetricList readBack = metricStorage.readMetric(filePath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back, without specifying file path
        original.add("Initiative", getTypicalAttributes(),
                getAttributePrefix(), getSamplemetricWeight());
        original.delete("Leader");
        metricStorage.saveMetrics(original);
        readBack = metricStorage.readMetric(filePath).get();
        assertEquals(original, readBack);
    }
    @Test
    public void saveMetrics_nullMetricList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMetric(null, "SomeFile.json"));
    }

    /**
     * Saves {@code MetricList} at the specified {@code filePath}.
     */
    private void saveMetric(MetricList metricList, String filePath) {
        try {
            new MetricStorage(Paths.get(filePath))
                    .saveMetrics(metricList);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMetrics_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMetric(new MetricList(), null));
    }
}
