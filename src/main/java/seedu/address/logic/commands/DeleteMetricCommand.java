package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hirelah.Metric;
import seedu.address.model.hirelah.MetricList;

/**
 * DeleteMetricCommand describes the behavior when the
 * client wants to delete an attribute from the list.
 */

public class DeleteMetricCommand extends DeleteCommand {
    public static final String COMMAND_WORD = "metric";
    public static final String MESSAGE_HAS_NOT_FINALIZED = "The session has not been finalized. Please finalize it"
            + " before deleting metrics.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the metric identified by its prefix.\n"
            + "Parameters: PREFIX\n"
            + "Example: delete " + COMMAND_WORD + " lea";

    public static final String MESSAGE_DELETE_METRIC_SUCCESS = "Deleted Metric with prefix: %1$s";

    private final String metricPrefix;

    public DeleteMetricCommand(String metricPrefix) {
        this.metricPrefix = metricPrefix;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isfinalisedInterviewProperties()) {
            throw new CommandException(MESSAGE_HAS_NOT_FINALIZED);
        }

        MetricList metrics = model.getMetricList();
        try {
            Metric metric = metrics.delete(metricPrefix);
            return new CommandResult(String.format(MESSAGE_DELETE_METRIC_SUCCESS,
                    metric), ToggleView.METRIC);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMetricCommand // instanceof handles nulls
                && metricPrefix.equals(((DeleteMetricCommand) other).metricPrefix)); // state check
    }
}
