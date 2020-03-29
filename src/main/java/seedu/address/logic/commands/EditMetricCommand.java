package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.AttributeList;
import seedu.address.model.hirelah.MetricList;

/**
 * EditMetricCommand describes the behavior of HireLah!
 * when a user wants to edit a metric.
 */

public class EditMetricCommand extends Command {
    public static final String COMMAND_WORD = "metric";
    public static final String MESSAGE_HAS_NOT_FINALIZED = "The session has not been finalized. Please finalize it"
            + " before editing metrics.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the metric identified by the prefix.\n"
            + "Parameters: IDENTIFIER NAME [-n NEW_NAME] [-a ATTRIBUTE_1] [-w WEIGHT_1]\n"
            + "Example: edit " + COMMAND_WORD + " extremeLeadership -n extremeDictatorship -a lea -w 100";

    public static final String MESSAGE_EDIT_METRIC_SUCCESS = "Successfully edited Metric";

    private final String toEdit;
    private final String updatedName;
    private final List<String> attributePrefixes;
    private final List<Double> weightages;

    public EditMetricCommand(String toEdit, String updatedName, List<String> attributePrefixes,
                             List<Double> weightages) {
        this.toEdit = toEdit;
        this.updatedName = updatedName;
        this.attributePrefixes = attributePrefixes;
        this.weightages = weightages;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isFinalisedInterviewProperties()) {
            throw new CommandException(MESSAGE_HAS_NOT_FINALIZED);
        }

        MetricList metrics = model.getMetricList();
        AttributeList attributes = model.getAttributeList();

        try {
            metrics.edit(toEdit, updatedName, attributes, attributePrefixes, weightages);
            return new CommandResult(MESSAGE_EDIT_METRIC_SUCCESS, ToggleView.METRIC);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
