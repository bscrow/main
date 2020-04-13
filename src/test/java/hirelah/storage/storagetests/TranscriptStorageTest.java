package hirelah.storage.storagetests;

import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalAttributes.getTypicalAttributes;
import static hirelah.testutil.TypicalInterviewee.getAnInterviewee;
import static hirelah.testutil.TypicalQuestions.getTypicalQns;
import static hirelah.testutil.TypicalTranscript.getTypicalTranscript;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import hirelah.commons.exceptions.DataConversionException;
import hirelah.model.hirelah.AttributeList;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.QuestionList;
import hirelah.model.hirelah.Transcript;
import hirelah.storage.TranscriptStorage;

public class TranscriptStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "TranscriptStorageTest");
    @TempDir
    public Path testFolder;

    @Test
    public void readTranscript_nullDirectory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTranscript(null, 1,
                getTypicalQns(), getTypicalAttributes()));
    }

    private java.util.Optional<Transcript> readTranscript(Path filePath, int id,
                                                          QuestionList questionList,
                                                          AttributeList attributeList) throws Exception {
        TranscriptStorage transcriptStorage = new TranscriptStorage(
                addToTestDataPathIfNotNull(filePath.toString()));
        return transcriptStorage.readTranscript(id, questionList, attributeList);
    }
    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? Paths.get(prefsFileInTestDataFolder)
                : null;
    }
    @Test
    public void read_invalidIntervieweeFile_emptyResult() throws Exception {
        assertFalse(readTranscript(TEST_DATA_FOLDER, 10, getTypicalQns(),
                getTypicalAttributes()).isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readTranscript(TEST_DATA_FOLDER, 1,
                getTypicalQns(), getTypicalAttributes()));
    }

    @Test
    public void readTranscript_invalidTranscript_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTranscript(TEST_DATA_FOLDER, 2,
                getTypicalQns(), getTypicalAttributes()));
    }

    @Test
    public void readTranscript_invalidAndValidTranscript_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTranscript(TEST_DATA_FOLDER, 3,
                getTypicalQns(), getTypicalAttributes()));
    }
    @Test
    public void readAndSaveTranscripts_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTranscript");
        Transcript original = getTypicalTranscript();
        Interviewee interviewee = getAnInterviewee();
        interviewee.setTranscript(original);
        TranscriptStorage transcriptStorage = new TranscriptStorage(filePath);

        // Save in new file and read back
        transcriptStorage.saveTranscript(interviewee);
        Transcript readBack = transcriptStorage.readTranscript(1, getTypicalQns(),
                getTypicalAttributes()).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back, without specifying file path
        original.addRemark("He is very experienced.", Duration.ofMinutes(1));
        interviewee = getAnInterviewee();
        interviewee.setTranscript(original);
        transcriptStorage.saveTranscript(interviewee);
        readBack = transcriptStorage.readTranscript(1, getTypicalQns(),
                getTypicalAttributes()).get();
        assertEquals(original, readBack);
    }
    @Test
    public void saveTranscripts_nullTranscriptList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTranscript(null, "SomeFile.json"));
    }

    /**
     * Saves {@code Transcript} at the specified {@code filePath}.
     */
    private void saveTranscript(Interviewee interviewee, String filePath) {
        try {
            new TranscriptStorage(Paths.get(filePath))
                    .saveTranscript(interviewee);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTranscripts_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTranscript(getAnInterviewee(), null));
    }
}
