package hirelah.logic.commands;

import static hirelah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hirelah.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_INTEGRITY;
import static hirelah.logic.commands.CommandTestUtility.VALID_ATTRIBUTE_TEAM_WORK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.Model;
import hirelah.model.ModelManager;
import hirelah.model.hirelah.AttributeList;

class AddAttributeCommandTest {

    @Test
    void execute_validAttribute_success() throws IllegalValueException {
        StorageStub storage = new StorageStub();
        Model actual = new ModelManager();
        actual.setAttributeList(new AttributeList());
        AttributeList expectedAttributeList = new AttributeList();
        expectedAttributeList.add(VALID_ATTRIBUTE_INTEGRITY);
        Model expected = new ModelManager();
        expected.setAttributeList(expectedAttributeList);
        AddAttributeCommand command = new AddAttributeCommand(VALID_ATTRIBUTE_INTEGRITY);
        CommandResult expectedCommandResult =
                new ToggleCommandResult(String.format(AddAttributeCommand.MESSAGE_SUCCESS, VALID_ATTRIBUTE_INTEGRITY),
                        ToggleView.ATTRIBUTE);

        assertCommandSuccess(command, actual, expectedCommandResult, expected, storage,
                storage::isAttributesSaved);
    }

    @Test
    void equals_differentAttribute_false() {
        AddAttributeCommand a1 = new AddAttributeCommand(VALID_ATTRIBUTE_TEAM_WORK);
        AddAttributeCommand a2 = new AddAttributeCommand(VALID_ATTRIBUTE_INTEGRITY);
        assertNotEquals(a1, a2);
    }

    @Test
    void equals_sameAttribute_true() {
        AddAttributeCommand a1 = new AddAttributeCommand(VALID_ATTRIBUTE_TEAM_WORK);
        AddAttributeCommand a2 = new AddAttributeCommand(VALID_ATTRIBUTE_TEAM_WORK);
        assertEquals(a1, a2);
    }
}
