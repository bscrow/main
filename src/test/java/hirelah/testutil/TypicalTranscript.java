package hirelah.testutil;

import static hirelah.testutil.TypicalAttributes.getTypicalAttributes;
import static hirelah.testutil.TypicalInterviewee.getAnInterviewee;
import static hirelah.testutil.TypicalQuestions.getTypicalQns;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.hirelah.Attribute;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.Transcript;
import hirelah.storage.TranscriptStorage;

/**A utility class containing a list of {@code Transcript}  to be used in tests.*/
public class TypicalTranscript {
    public static final String REMARK1 = "He is very good with JAVA:)";
    public static final String REMARK2 = "He is very okay with python:(";
    public static final Instant NOW = Instant.parse("2007-12-03T10:15:30.00Z");
    public static final Duration TIME1 = Duration.ofMinutes(3);
    public static final Duration TIME2 = Duration.ofMinutes(15);

    public static Transcript getTypicalTranscript() {
        Transcript transcript = new Transcript(getTypicalQns(), getTypicalAttributes(), NOW);
        transcript.addRemark(REMARK1, TIME1);
        transcript.addRemark(REMARK2, TIME2);
        double score = 1.0;
        for (Attribute attribute: getTypicalAttributes()) {
            transcript.setAttributeScore(attribute, score++);
        }
        return transcript;
    }

    /** static method to create a Json file for transcript*/
    public static void generateTranscriptJson(Path path) throws IOException, IllegalValueException {
        TranscriptStorage transcriptStorage = new TranscriptStorage(path);
        Interviewee interviewee = getAnInterviewee();
        interviewee.setTranscript(getTypicalTranscript());
        transcriptStorage.saveTranscript(interviewee);
    }

}
