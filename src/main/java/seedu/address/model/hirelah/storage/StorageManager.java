package seedu.address.model.hirelah.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Model;

/**
 * Manages storage of different Sessions data in local storage.
 */
public class StorageManager implements Storage {
    private static final Logger logger = LogsCenter.getLogger(seedu.address.storage.StorageManager.class);

    private Path filePath;

    public StorageManager(Path filePath) {
        this.filePath = filePath;
    }


    public Path getFilePath() {
        return filePath;
    }
    /**
     * Save the current Session
     * @param  model of the data. Cannot be null.
     */
    public void saveSession(Model model) throws IOException {
        requireNonNull(model);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableModel(model), filePath);
    }

    /**
     * reads the data from the current Path of the storage.
     * @return Model
     * @throws DataConversionException
     */
    public Optional<Model> readModel() throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableModel> jsonModel = JsonUtil.readJsonFile(
                filePath, JsonSerializableModel.class);
        if (jsonModel.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonModel.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }


}
