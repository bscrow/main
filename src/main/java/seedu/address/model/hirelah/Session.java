package seedu.address.model.hirelah;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.hirelah.storage.Storage;

/**
 * This class encapsulates the Storage class and is responsible for
 * loading the data before each interview session and saving the data before the end of the session.
 */
public class Session {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final Storage storage;
    //private final UserPrefsStorage userPrefsStorage;

    public Session(Storage initialStorageData/*, UserPrefsStorage initialUserPrefsData*/) {
        this.storage = initialStorageData;
        //this.userPrefsStorage = initialUserPrefsData;
    }

    /**
     * Load any existing model.
     * @return Model data as a {@link Model}.
     */

    public Model initModelManager() {
        Optional<Model> optionalModel;
        Model initialData;
        try {
            optionalModel = storage.readModel();
            if (optionalModel.isEmpty()) {
                logger.info("Data file not found. Will be starting with an empty session.");
            }
            initialData = optionalModel.orElseGet(() -> new ModelManager(new UserPrefs()));
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty session");
            initialData = new ModelManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with a new session");
            initialData = new ModelManager();
        }
        return initialData;
    }

    /**
     * Save the current Session.
     * @param model the current model at {@link Model}.
     * @throws IOException when the file is not valid.
     */
    public void saveSession(Model model) throws IOException {
        storage.saveSession(model);
    }
}
