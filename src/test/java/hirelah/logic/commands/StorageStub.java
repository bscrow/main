package hirelah.logic.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import hirelah.commons.exceptions.DataConversionException;
import hirelah.model.Model;
import hirelah.model.ReadOnlyUserPrefs;
import hirelah.model.UserPrefs;
import hirelah.model.hirelah.AttributeList;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.IntervieweeList;
import hirelah.model.hirelah.MetricList;
import hirelah.model.hirelah.QuestionList;
import hirelah.storage.Storage;

/**
 * A stub to be used to test if Command classes save the correct components
 */
public class StorageStub implements Storage {
    private boolean intervieweesSaved;
    private boolean attributesSaved;
    private boolean questionsSaved;
    private boolean metricsSaved;
    private int transcriptSaved; // int to indicate which interviewee id, 0 if not saved
    private boolean modelSaved;

    public boolean isIntervieweesSaved() {
        return intervieweesSaved;
    }

    public boolean isAttributesSaved() {
        return attributesSaved;
    }

    public boolean isQuestionsSaved() {
        return questionsSaved;
    }

    public boolean isMetricsSaved() {
        return metricsSaved;
    }

    public int getTranscriptSaved() {
        return transcriptSaved;
    }

    public boolean isModelSaved() {
        return modelSaved;
    }

    @Override
    public List<File> readSessions(ReadOnlyUserPrefs userPrefs) throws IOException {
        return null;
    }

    @Override
    public void loadSession(Model model, Path session) throws DataConversionException {

    }

    @Override
    public void saveInterviewee(IntervieweeList source) throws IOException {
        intervieweesSaved = true;
    }

    @Override
    public Optional<IntervieweeList> readInterviewee(QuestionList questionList,
                                                     AttributeList attributeList,
                                                     Boolean initialModel) throws DataConversionException {
        return Optional.empty();
    }

    @Override
    public void saveAttribute(AttributeList source) throws IOException {
        attributesSaved = true;
    }

    @Override
    public Optional<AttributeList> readAttribute() throws DataConversionException {
        return Optional.empty();
    }

    @Override
    public Optional<AttributeList> readAttribute(Path session) throws DataConversionException {
        return Optional.empty();
    }

    @Override
    public void saveQuestion(QuestionList source) throws IOException {
        questionsSaved = true;
    }

    @Override
    public Optional<QuestionList> readQuestion() throws DataConversionException {
        return Optional.empty();
    }

    @Override
    public Optional<QuestionList> readQuestion(Path session) throws DataConversionException {
        return Optional.empty();
    }

    @Override
    public void saveMetric(MetricList source) throws IOException {
        metricsSaved = true;
    }

    @Override
    public Optional<MetricList> readMetric() throws DataConversionException {
        return Optional.empty();
    }

    @Override
    public void saveTranscript(Interviewee source) throws IOException {
        transcriptSaved = source.getId();
    }

    @Override
    public void saveModel(Boolean model) throws IOException {
        modelSaved = true;
    }

    @Override
    public Optional<Boolean> readModel() throws DataConversionException {
        return Optional.empty();
    }

    @Override
    public Path getUserPrefsFilePath() {
        return null;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException {
        return Optional.empty();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {

    }
}
