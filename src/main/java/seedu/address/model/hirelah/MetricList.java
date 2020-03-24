package seedu.address.model.hirelah;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.exceptions.IllegalValueException;


/*
 * MetricList
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 01 Mar 2020
 *
 */

/**
 * <p>MetricList class manages the list of metrics that have been
 * added by the interviewer. MetricList can retrieve the attributes using
 * the prefix.</p>
 * @author AY1920S2-W15-2
 */

public class MetricList {
    private static final String ALREADY_EXISTS_MESSAGE = "This metric is already exists!";
    private static final String DUPLICATE_MESSAGE = "There are multiple metrics with the same prefix.";
    private static final String INCOMPLETE_MESSAGE = "The number of attributes and the number of weights is not equal.";
    private static final String NOT_FOUND_MESSAGE = "No metrics with the entered prefix.";

    private ObservableList<Metric> metrics;

    /**
     * Constructs a MetricList instance.
     */
    public MetricList() {
        this.metrics = FXCollections.observableArrayList();
    }

    public ObservableList<Metric> getObservableList() {
        return metrics;
    }

    /**
     * Adds the metric to the list.
     * @param metricName The metric name.
     * @throws IllegalValueException If the name of the metric already exists.
     */

    public void add(String metricName, AttributeList attributes,
                    List<String> attributePrefixes, List<Double> weightages) throws IllegalValueException {
        if (!isEqualSizeMapping(attributePrefixes, weightages)) {
            throw new IllegalValueException(INCOMPLETE_MESSAGE);
        }
        HashMap<Attribute, Double> attributeToScore = new HashMap<>();
        for (int i = 0; i < attributePrefixes.size(); i++) {
            Attribute attribute = attributes.find(attributePrefixes.get(i));
            attributeToScore.put(attribute, weightages.get(i));
        }
        Metric metric = Metric.of(metricName, attributeToScore);
        boolean isDuplicate = isDuplicate(metric);

        if (isDuplicate) {
            throw new IllegalValueException(ALREADY_EXISTS_MESSAGE);
        }

        metrics.add(metric);
    }

    /**
     * Edits the name or the weightage of a metric.
     * @param metricPrefix The prefix of the current metric.
     * @param updatedName The updated name of the metric.
     * @param attributes The list of attributes that is available in a sessiong.
     * @param attributePrefixes The list of attribute prefixes.
     * @param weightages The list of the weightages that wants to be added.
     * @throws IllegalValueException If there is a formatting error in the command.
     */

    public void edit(String metricPrefix, String updatedName, AttributeList attributes,
                     List<String> attributePrefixes, List<Double> weightages) throws IllegalValueException {
        Metric metric = find(metricPrefix);
        Metric updatedMetric = metric.setName(updatedName);

        for (int i = 0; i < attributePrefixes.size(); i++) {
            Attribute attribute = attributes.find(attributePrefixes.get(i));
            updatedMetric.setValueToAttribute(attribute, weightages.get(i));
        }

        int index = metrics.indexOf(metric);
        metrics.set(index, updatedMetric);
    }

    /**
     * Find the metric based on its prefix.
     * @param metricPrefix The prefix of the metric.
     * @return The corresponding Attribute instance.
     * @throws IllegalValueException if the prefix can be multi-interpreted or no such Metric found.
     */

    public Metric find(String metricPrefix) throws IllegalValueException {
        Optional<Metric> exactMetric = metrics.stream().filter(metric -> metric.toString()
                .equals(metricPrefix))
                .findFirst();
        if (exactMetric.isEmpty()) {
            checkPrefix(metricPrefix);
            return metrics.stream().filter(metric -> metric.toString().startsWith(metricPrefix))
                    .findFirst()
                    .get();
        } else {
            return exactMetric.get();
        }

    }

    /**
     * Deletes the metric by its prefix.
     * @param metricPrefix The prefix of the attribute.
     * @return The outcome message.
     * @throws IllegalValueException if the prefix can be multi-interpreted or no such Attribute found.
     */

    public Metric delete(String metricPrefix) throws IllegalValueException {
        return find(metricPrefix);
    }

    /**
     * Checks the number of metrics that starts with the prefix.
     * @param metricPrefix The prefix of the attribute.
     * @throws IllegalValueException if the prefix can be multi-interpreted or no such Metric found.
     */

    private void checkPrefix(String metricPrefix) throws IllegalValueException {
        long startWithPrefix = metrics.stream()
                .filter(metric -> metric.toString().startsWith(metricPrefix))
                .count();

        if (startWithPrefix > 1) {
            throw new IllegalValueException(DUPLICATE_MESSAGE);
        } else if (startWithPrefix == 0) {
            throw new IllegalValueException(NOT_FOUND_MESSAGE);
        }
    }

    private boolean isEqualSizeMapping(List<String> attributePrefixes, List<Double> weightages) {
        return attributePrefixes.size() == weightages.size();
    }

    /**
     * Checks whether the checklist is complete or not.
     * @param checklist The checklist of whether all attributes are listed by the client.
     * @return The corresponding result.
     */
    private boolean isNotCompleteChecklist(HashMap<Attribute, Boolean> checklist) {
        boolean result = true;

        for (Map.Entry<Attribute, Boolean> entry : checklist.entrySet()) {
            result = result && entry.getValue();
        }

        return !result;
    }

    /**
     * Builds a checklist from the attribute list.
     * @param attributes The attribute list.
     * @return The checklist of whether an attribute's score is stated.
     */
    private HashMap<Attribute, Boolean> initiateChecklist(AttributeList attributes) {
        ObservableList<Attribute> attributeList = attributes.getObservableList();
        HashMap<Attribute, Boolean> hashMap = new HashMap<>();

        for (Attribute attribute: attributeList) {
            hashMap.put(attribute, false);
        }

        return hashMap;
    }

    private boolean isDuplicate(Metric metric) {
        return metrics.stream().anyMatch(metric::equals);
    }
}
