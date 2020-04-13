package hirelah.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.BooleanSupplier;

import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.storage.Storage;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     * - the required components are saved
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel, Storage storage, BooleanSupplier... savedComponents) {
        try {
            CommandResult result = command.execute(actualModel, storage);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            for (BooleanSupplier saved : savedComponents) {
                assertTrue(saved.getAsBoolean());
            }
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

}
