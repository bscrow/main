package hirelah.storage.serialisetests;

import static hirelah.commons.util.JsonUtil.readJsonFile;
import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalQuestionList.getTypicalQns;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.commons.util.JsonUtil;
import hirelah.model.hirelah.QuestionList;
import hirelah.storage.JsonSerializableQuestion;

public class JsonSerializableQuestionTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableQuestionTest");
    private static final Path TYPICAL_QUESTION_FILE = TEST_DATA_FOLDER.resolve("ValidQuestion.json");
    private static final Path INVALID_QUESTION_FILE = TEST_DATA_FOLDER.resolve("InvalidQuestion.json");
    private static final Path DUPLICATE_QUESTION_FILE = TEST_DATA_FOLDER.resolve("DuplicateQuestion.json");


    @Test
    public void toModelType_validQuestionFile_success() throws Exception {
        JsonSerializableQuestion dataFromFile = readJsonFile(TYPICAL_QUESTION_FILE ,
                JsonSerializableQuestion.class).get();
        QuestionList questionListFromFile = dataFromFile.toModelType();
        assertEquals(getTypicalQns(), questionListFromFile);
    }

    @Test
    public void toModelType_invalidQuestionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableQuestion dataFromFile = JsonUtil.readJsonFile(INVALID_QUESTION_FILE,
                JsonSerializableQuestion.class).get();
        assertThrows(IllegalValueException.class, () -> {
            dataFromFile.toModelType();
        });
    }

    @Test
    public void toModelType_duplicateQuestionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableQuestion dataFromFile = JsonUtil.readJsonFile(DUPLICATE_QUESTION_FILE,
                JsonSerializableQuestion.class).get();
        assertThrows(IllegalValueException.class, () -> {
            dataFromFile.toModelType();
        });
    }
}
