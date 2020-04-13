package hirelah.storage.storagetests;

import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalQuestionList.getTypicalQns;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import hirelah.commons.exceptions.DataConversionException;
import hirelah.model.hirelah.QuestionList;
import hirelah.storage.QuestionStorage;

public class QuestionStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "QuestionStorageTest");
    @TempDir
    public Path testFolder;

    @Test
    public void readQuestion_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readQuestion(null));
    }

    private java.util.Optional<QuestionList> readQuestion(String filePath) throws Exception {
        QuestionStorage questionStorage = new QuestionStorage(Paths.get(filePath));
        return questionStorage.readQuestion(addToTestDataPathIfNotNull(filePath));

    }
    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }
    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readQuestion("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readQuestion("notJsonFormat.json"));
    }

    @Test
    public void readQuestion_invalidQuestionList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readQuestion("invalidQuestion.json"));
    }

    @Test
    public void readQuestion_invalidAndValidQuestion_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readQuestion("invalidAndValidQuestion.json"));
    }
    @Test
    public void readAndSaveQuestions_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempQuestionList.json");
        QuestionList original = getTypicalQns();
        QuestionStorage questionStorage = new QuestionStorage(filePath);

        // Save in new file and read back
        questionStorage.saveQuestions(original);
        QuestionList readBack = questionStorage.readQuestion(filePath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back, without specifying file path
        original.add("How are you currently doing?");
        original.delete(1);
        questionStorage.saveQuestions(original);
        readBack = questionStorage.readQuestion(filePath).get();
        assertEquals(original, readBack);
    }
    @Test
    public void saveQuestions_nullQuestionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveQuestion(null, "SomeFile.json"));
    }

    /**
     * Saves {@code QuestionList} at the specified {@code filePath}.
     */
    private void saveQuestion(QuestionList questionList, String filePath) {
        try {
            new QuestionStorage(Paths.get(filePath))
                    .saveQuestions(questionList);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveQuestions_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveQuestion(new QuestionList(), null));
    }
}
