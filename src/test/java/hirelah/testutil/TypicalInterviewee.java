package hirelah.testutil;

import static hirelah.testutil.TypicalTranscript.getTypicalTranscript;

import java.util.List;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.IntervieweeList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** A utility class to create Interviewee and InterviewList to be used in tests.*/
public class TypicalInterviewee {
    public static final String NAME1 = "Tom";
    public static final String NAME2 = "JANE";
    public static final int ID1 = 1;
    public static final int ID2 = 2;
    public static final String ALIAS1 = "lucky boy";
    public static final String ALIAS2 = "lucky girl";

    /**create a list of interviewee that has been interviewed*/
    public static IntervieweeList getIntervieweeList() throws IllegalValueException {
        List<Interviewee> list;
        Interviewee firstInterviewee = new Interviewee(NAME1, ID1);
        firstInterviewee.setAlias(ALIAS1);
        firstInterviewee.setTranscript(getTypicalTranscript());
        Interviewee secondInterviewee = new Interviewee(NAME2, ID2);
        secondInterviewee.setAlias(ALIAS2);
        secondInterviewee.setTranscript(getTypicalTranscript());
        list = List.of(firstInterviewee, secondInterviewee);
        return IntervieweeList.fromList(3, list);
    }
    /**create a list of interviewee that has yet to be interviewed*/
    public static IntervieweeList intervieweeBeforeInterview() throws IllegalValueException {
        List<Interviewee> list;
        Interviewee firstInterviewee = new Interviewee(NAME1, ID1);
        firstInterviewee.setAlias(ALIAS1);
        Interviewee secondInterviewee = new Interviewee(NAME2, ID2);
        secondInterviewee.setAlias(ALIAS2);
        list = List.of(firstInterviewee, secondInterviewee);
        return IntervieweeList.fromList(3, list);
    }
    /**Return an interviewee who is yet to be interviewed*/
    public static Interviewee getAnInterviewee() throws IllegalValueException {
        Interviewee firstInterviewee = new Interviewee(NAME1, ID1);
        firstInterviewee.setAlias(ALIAS1);
        return firstInterviewee;
    }
    /**Return a different interviewee who is yet to be interviewed*/
    public static Interviewee getAnotherInterviewee() throws IllegalValueException {
        Interviewee firstInterviewee = new Interviewee(NAME2, ID2);
        firstInterviewee.setAlias(ALIAS2);
        return firstInterviewee;
    }
    /**Returns an ObservableList of interviewees who have yet to be interviewed*/
    public static ObservableList<Interviewee> getObservableIntervieweeList() throws IllegalValueException {
        Interviewee firstInterviewee = new Interviewee(NAME1, ID1);
        firstInterviewee.setAlias(ALIAS1);
        Interviewee secondInterviewee = new Interviewee(NAME2, ID2);
        secondInterviewee.setAlias(ALIAS2);
        return FXCollections.observableArrayList(List.of(firstInterviewee, secondInterviewee));
    }
}
