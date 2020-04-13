package hirelah.storage.serialisetests;

import static hirelah.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.commons.util.JsonUtil;
import hirelah.storage.JsonSerializableModel;

public class JsonSerializableModelTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableModelTest");
    private static final Path INVALID_MODEL_FILE = TEST_DATA_FOLDER.resolve("InvalidModel.json");
    private static final Path VALID_MODEL_FILE = TEST_DATA_FOLDER.resolve("ValidModel.json");

    @Test
    public void toModelType_validModelFile_throwsIllegalValueException() throws Exception {
        JsonSerializableModel dataFromFile = JsonUtil.readJsonFile(VALID_MODEL_FILE,
                JsonSerializableModel.class).get();
        Boolean modelFromFile = dataFromFile.toModelType();
        assertEquals(Boolean.TRUE, modelFromFile);
    }
    @Test
    public void toModelType_invalidModelFile_throwsIllegalValueException() throws Exception {
        JsonSerializableModel dataFromFile = JsonUtil.readJsonFile(INVALID_MODEL_FILE,
                JsonSerializableModel.class).get();
        assertThrows(IllegalValueException.class, () -> {
            dataFromFile.toModelType();
        });
    }

}
