package hirelah.storage.storagetests;

import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalAttributes.getTypicalAttributes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import hirelah.commons.exceptions.DataConversionException;
import hirelah.model.hirelah.AttributeList;
import hirelah.storage.AttributeStorage;

public class AttributeStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "AttributeStorageTest");
    @TempDir
    public Path testFolder;

    @Test
    public void readAttribute_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAttribute(null));
    }

    private java.util.Optional<AttributeList> readAttribute(String filePath) throws Exception {
        AttributeStorage attributeStorage = new AttributeStorage(Paths.get(filePath));
        return attributeStorage.readAttribute(addToTestDataPathIfNotNull(filePath));

    }
    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }
    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAttribute("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAttribute("notJsonFormat.json"));
    }

    @Test
    public void readAttribute_invalidAttributeList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAttribute("invalidAttribute.json"));
    }

    @Test
    public void readAttribute_invalidAndValidAttribute_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAttribute("invalidAndValidAttribute.json"));
    }
    @Test
    public void readAndSaveAttributes_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAttributeList.json");
        AttributeList original = getTypicalAttributes();
        AttributeStorage attributeStorage = new AttributeStorage(filePath);

        // Save in new file and read back
        attributeStorage.saveAttributes(original);
        AttributeList readBack = attributeStorage.readAttribute(filePath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back, without specifying file path
        original.add("Initiative");
        original.delete("Leadership");
        attributeStorage.saveAttributes(original);
        readBack = attributeStorage.readAttribute(filePath).get();
        assertEquals(original, readBack);
    }
    @Test
    public void saveAttributes_nullAttributeList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAttribute(null, "SomeFile.json"));
    }

    /**
     * Saves {@code AttributeList} at the specified {@code filePath}.
     */
    private void saveAttribute(AttributeList attributeList, String filePath) {
        try {
            new AttributeStorage(Paths.get(filePath))
                    .saveAttributes(attributeList);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAttributes_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAttribute(new AttributeList(), null));
    }
}
