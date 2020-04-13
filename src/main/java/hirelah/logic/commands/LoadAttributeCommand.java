package hirelah.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import hirelah.commons.exceptions.DataConversionException;
import hirelah.commons.util.ModelUtil;
import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.model.hirelah.AttributeList;
import hirelah.storage.Storage;


/**
 * LoadAttributeCommand describes the behavior of the command
 * to load attributes from other session.
 */

public class LoadAttributeCommand extends Command {
    public static final String COMMAND_WORD = "attribute";
    public static final boolean DESIRED_MODEL_FINALIZED_STATE = false;
    public static final String JSON_FILE = "attribute.json";
    public static final String MESSAGE_JSON_NOT_FOUND = "The json file is not found";
    public static final String MESSAGE_FORMAT = "load " + COMMAND_WORD + " <session>";
    public static final String MESSAGE_FUNCTION = ": Loads the attributes from another session\n";
    public static final String MESSAGE_SESSION_NOT_EXIST = "The session does not exist";
    public static final String MESSAGE_NOT_ABLE_TO_CONVERT = "The attribute list is failed to be converted";
    public static final String MESSAGE_USAGE = MESSAGE_FORMAT
            + MESSAGE_FUNCTION
            + "Example: load " + COMMAND_WORD + " CEOInterview";

    public static final String MESSAGE_LOAD_ATTRIBUTE_SUCCESS = "Loaded attributes from %s";

    private final String session;

    public LoadAttributeCommand(String session) {
        this.session = session;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);
        ModelUtil.validateFinalisation(model, DESIRED_MODEL_FINALIZED_STATE);

        File sessionDir = new File(model.getSessionsDirectory().toFile(), session);

        if (!sessionDir.isDirectory()) {
            throw new CommandException(MESSAGE_SESSION_NOT_EXIST);
        }

        File attributeFile = new File(sessionDir, JSON_FILE);

        if (!attributeFile.exists()) {
            throw new CommandException(MESSAGE_JSON_NOT_FOUND);
        }

        Path sessionPath = attributeFile.toPath();
        try {
            Optional<AttributeList> optionalAttributes = storage.readAttribute(sessionPath);
            AttributeList currentAttributes = model.getAttributeList();
            currentAttributes.setAll(optionalAttributes.orElse(new AttributeList()));
            storage.saveAttribute(currentAttributes);
            return new ToggleCommandResult(String.format(MESSAGE_LOAD_ATTRIBUTE_SUCCESS, session),
                    ToggleView.ATTRIBUTE);

        } catch (DataConversionException | IOException e) {
            throw new CommandException(MESSAGE_NOT_ABLE_TO_CONVERT);
        }
    }
}
