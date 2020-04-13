package hirelah.storage.serialisetests;

import static hirelah.commons.util.JsonUtil.readJsonFile;
import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalAttributes.getTypicalAttributes;
import static hirelah.testutil.TypicalQuestions.getTypicalQns;
import static hirelah.testutil.TypicalTranscript.getTypicalTranscript;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.commons.util.JsonUtil;
import hirelah.model.hirelah.Transcript;
import hirelah.storage.JsonSerializableTranscript;

public class JsonSerializableTranscriptTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTranscriptTest");
    private static final Path TYPICAL_TRANSCRIPT_FILE = TEST_DATA_FOLDER.resolve("ValidTranscript.json");
    private static final Path INVALID_TRANSCRIPT_FILE = TEST_DATA_FOLDER.resolve("InvalidTranscript.json");

    @Test
    public void toModelType_validTranscriptFile_success() throws Exception {
        JsonSerializableTranscript dataFromFile = readJsonFile(TYPICAL_TRANSCRIPT_FILE,
                JsonSerializableTranscript.class).get();
        Transcript transcriptFromFile = dataFromFile.toModelType(getTypicalQns(), getTypicalAttributes());
        assertEquals(getTypicalTranscript(), transcriptFromFile);
    }

    @Test
    public void toModelType_invalidTranscriptFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTranscript dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSCRIPT_FILE,
                JsonSerializableTranscript.class).get();
        assertThrows(IllegalValueException.class, () -> {
            dataFromFile.toModelType(getTypicalQns(), getTypicalAttributes());
        });
    }
}
