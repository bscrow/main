package seedu.address.model.hirelah;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/*
 * Metric
 *
 * CS2103 AY19/20 Semester 2
 * Team Project
 * HireLah!
 *
 * 02 Mar 2020
 *
 */

/**
 * <p>Metric class abstracts the comparator of the interviewee
 * based on the respective weight of the attributes.</p>
 * @author AY1920S2-W15-2
 */

public class Metric {
    private String name;
    private HashMap<Attribute, Double> attributeToWeight;

    /**
     * Constructs a Metric instance.
     * @param name The name of the metric.
     */
    public Metric(String name) {
        this.name = name;
        this.attributeToWeight = new HashMap<>();
    }

    /**
     * Set a particular attribute to a value.
     * @param attribute The attribute that the value wants to be set.
     * @param value The value of the attribute.
     * @return The outcome message.
     */
    public String setValueToAttribute(Attribute attribute, double value) {
        this.attributeToWeight.put(attribute, value);
        return String.format("Successfully set the value of attribute %s to %.2f", attribute, value);
    }

    /**
     * Constructs a Comparator for Interviewee based on the weighted score.
     * @return The Comparator instance.
     */
    public Comparator<Interviewee> getComparator() {
        return (interviewee, anotherInterviewee) -> {
            double totalScoreFirst = computeScore(interviewee);
            double totalScoreSecond = computeScore(anotherInterviewee);
            return totalScoreSecond - totalScoreFirst < 0 ? -1 : totalScoreFirst == totalScoreSecond ? 0 : 1;
        };
    }

    /**
     * Computes the final score of an interviewee. Can only be called on Interviewees that have been
     * interviewed.
     *
     * @param interviewee The interviewee to be computed.
     * @return The score of the interviewee based on the metric.
     */
    private double computeScore(Interviewee interviewee) {
        double totalScore = 0;
        for (Map.Entry<Attribute, Double> mapElement : attributeToWeight.entrySet()) {
            Attribute attribute = mapElement.getKey();
            double weight = mapElement.getValue();

            totalScore += weight * interviewee.getScore(attribute);
        }
        return totalScore;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Metric // instanceof handles nulls
                && name.equals(((Metric) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
