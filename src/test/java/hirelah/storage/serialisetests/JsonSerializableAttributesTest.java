package hirelah.storage.serialisetests;

import static hirelah.commons.util.JsonUtil.readJsonFile;
import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalAttributes.getTypicalAttributes;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.commons.util.JsonUtil;
import hirelah.model.hirelah.AttributeList;
import hirelah.storage.JsonSerializableAttributes;

public class JsonSerializableAttributesTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAttributeTest");
    private static final Path TYPICAL_ATTRIBUTE_FILE = TEST_DATA_FOLDER.resolve("ValidAttribute.json");
    private static final Path INVALID_ATTRIBUTE_FILE = TEST_DATA_FOLDER.resolve("InvalidAttribute.json");
    private static final Path DUPLICATE_ATTRIBUTE_FILE = TEST_DATA_FOLDER.resolve("DuplicateAttribute.json");

    @Test
    public void toModelType_validAttributeFile_success() throws Exception {
        JsonSerializableAttributes dataFromFile = readJsonFile(TYPICAL_ATTRIBUTE_FILE ,
                JsonSerializableAttributes.class).get();
        AttributeList attributeListFromFile = dataFromFile.toModelType();
        assertEquals(getTypicalAttributes(), attributeListFromFile);
    }

    @Test
    public void toModelType_invalidAttributeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAttributes dataFromFile = JsonUtil.readJsonFile(INVALID_ATTRIBUTE_FILE,
                JsonSerializableAttributes.class).get();
        assertThrows(IllegalValueException.class, () -> {
            dataFromFile.toModelType();
        });
    }

    @Test
    public void toModelType_duplicateAttributeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAttributes dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ATTRIBUTE_FILE,
                JsonSerializableAttributes.class).get();
        assertThrows(IllegalValueException.class, () -> {
            dataFromFile.toModelType();
        });
    }
}
