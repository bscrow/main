package hirelah.storage.storagetests;

import static hirelah.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import hirelah.commons.exceptions.DataConversionException;
import hirelah.storage.ModelStorage;

public class ModelStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "ModelStorageTest");
    @TempDir
    public Path testFolder;

    @Test
    public void readModel_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readModel(null));
    }

    private java.util.Optional<Boolean> readModel(String filePath) throws Exception {
        ModelStorage modelStorage = new ModelStorage(Paths.get(filePath));
        return modelStorage.readModel(addToTestDataPathIfNotNull(filePath));

    }
    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }
    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readModel("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readModel("notJsonFormat.json"));
    }

    @Test
    public void readModel_invalidModelList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readModel("invalidModel.json"));
    }

    @Test
    public void readAndSaveModels_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempModel.json");
        Boolean original = true;
        ModelStorage modelStorage = new ModelStorage(filePath);

        // Save in new file and read back
        modelStorage.saveModel(original);
        Boolean readBack = modelStorage.readModel(filePath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back, without specifying file path
        original = false;
        modelStorage.saveModel(original);
        readBack = modelStorage.readModel(filePath).get();
        assertEquals(original, readBack);
    }
    @Test
    public void saveModels_nullModelList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModel(null, "SomeFile.json"));
    }

    /** Method deployed to test for ModelStorage.*/
    private void saveModel(Boolean model, String filePath) {
        try {
            new ModelStorage(Paths.get(filePath))
                    .saveModel(model);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveModels_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModel(true, null));
    }
}
