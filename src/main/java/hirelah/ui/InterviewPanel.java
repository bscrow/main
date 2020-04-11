package hirelah.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import hirelah.commons.core.LogsCenter;
import hirelah.logic.Logic;
import hirelah.logic.commands.ToggleView;
import hirelah.model.hirelah.Interviewee;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;


/**
 * InterviewPanel which contains all the information regarding an interview session.
 */
public class InterviewPanel extends UiPart<Region> {
    private static final String FXML = "InterviewPanel.fxml";
    private static final String PRO_TIP = "Pro Tip:\n"
            + "      Start by setting some interview rubrics (questions, \n"
            + "        attributes, metrics) to evaluate interviewees. \n"
            + "      Type \"interview <interviewee>\" to start an interview.\n"
            + "      Type \"help\" to get help.";

    private final Logger logger = LogsCenter.getLogger(InterviewPanel.class);
    private final CommandExecutor commandExecutor;
    private final Logic logic;

    // Independent Ui parts residing in this Ui container
    private IntervieweeListPanel intervieweeListPanel;
    private AttributeListPanel attributeListPanel;
    private MetricListPanel metricListPanel;
    private QuestionListPanel questionListPanel;
    private RemarkListPanel remarkListPanel;
    private SessionInformationCard sessionInformationCard;

    @FXML
    private AnchorPane intervieweePane;
    @FXML
    private AnchorPane transcriptPane;
    @FXML
    private Label emptyTranscriptPaneLabel;
    @FXML
    private TabPane rubricsPane;
    @FXML
    private Tab attributesTab;
    @FXML
    private Tab questionsTab;
    @FXML
    private Tab metricsTab;

    public InterviewPanel(Logic logic, CommandExecutor commandExecutor) {
        super(FXML);
        this.logic = logic;
        this.commandExecutor = commandExecutor;

        intervieweeListPanel = new IntervieweeListPanel(logic.getIntervieweeListView());
        attributeListPanel = new AttributeListPanel(logic.getAttributeListView());
        questionListPanel = new QuestionListPanel(logic.getQuestionListView());
        metricListPanel = new MetricListPanel(logic.getMetricListView());
        fillInnerParts();

    }

    /**
     * Fills up this window with all the UI components that will be shown by default.
     */
    private void fillInnerParts() {
        loadIntervieweePanel();
        loadSessionInformationCard();

        emptyTranscriptPaneLabel.setText(PRO_TIP);

        setAnchor(rubricsPane, 0.0, 0.0, 3.0, 0.0);
        attributesTab.setContent(attributeListPanel.getRoot());
        questionsTab.setContent(questionListPanel.getRoot());
        metricsTab.setContent(metricListPanel.getRoot());
    }

    /**
     * Loads the intervieweePane on the left side of the window with the list of all interviewees.
     */
    private void loadIntervieweePanel() {
        intervieweePane.getChildren().clear();
        setAnchor(intervieweeListPanel.getRoot(), 0.0, 0.0, 5.0, 5.0);
        intervieweePane.getChildren().add(intervieweeListPanel.getRoot());
    }

    /**
     * Loads the intervieweePane on the left side of the window with the list of the best n interviewees based on the
     * user's metric.
     */
    private void loadBestIntervieweePanel() {
        intervieweePane.getChildren().clear();
        BestIntervieweeListPanel bestNIntervieweesPanel =
                new BestIntervieweeListPanel(logic.getBestNIntervieweesView());
        setAnchor(bestNIntervieweesPanel.getRoot(), 0.0, Double.NaN, 5.0, 5.0);
        intervieweePane.getChildren().add(bestNIntervieweesPanel.getRoot());
        intervieweePane.getChildren().add(sessionInformationCard.getRoot());
    }

    /**
     * Loads the {@code Transcript} of an {@code Interviewee} selected by the user.
     *
     * @param currentInterviewee Interviewee selected by the user.
     */
    private void loadTranscript(Interviewee currentInterviewee) {
        transcriptPane.getChildren().clear();
        DetailedIntervieweeCard detailedIntervieweeCard =
                new DetailedIntervieweeCard(currentInterviewee);
        remarkListPanel = new RemarkListPanel(currentInterviewee, logic.getQuestionListView());
        transcriptPane.getChildren().add(remarkListPanel.getRoot());
        setAnchor(remarkListPanel.getRoot(), 5.0, 0.0, 5.0, 5.0);
        transcriptPane.getChildren().add(detailedIntervieweeCard.getRoot());
        setAnchor(detailedIntervieweeCard.getRoot(), Double.NaN, 0.0, Double.NaN, 5.0);
    }

    /**
     * Loads a pane showing information about the current interview session.
     */
    private void loadSessionInformationCard() {
        requireNonNull(logic.getCurrentSession());
        sessionInformationCard = new SessionInformationCard(logic.getCurrentSession().get(),
                logic.getIntervieweeListView(),
                logic.isFinalisedInterviewProperties());
        setAnchor(sessionInformationCard.getRoot(), 0.0, 0.0, 5.0, Double.NaN);
        intervieweePane.getChildren().add(sessionInformationCard.getRoot());
    }

    /**
     * Sets what is displayed in the listPanelStackPane based on the toggle.
     *
     * @param toggleView enum representing what should be displayed.
     */
    public void handleToggle(ToggleView toggleView) {
        switch (toggleView) {
        case INTERVIEWEE: // interviewee
            loadIntervieweePanel();
            loadSessionInformationCard();
            break;
        case BEST_INTERVIEWEE: // best interviewees
            loadBestIntervieweePanel();
            break;
        case TRANSCRIPT: // showing a transcript
            loadTranscript(logic.getCurrentInterviewee());
            break;
        case CLOSE_TRANSCRIPT: // closing the currently shown transcript
            transcriptPane.getChildren().clear();
            transcriptPane.getChildren().add(emptyTranscriptPaneLabel);
            logic.setCurrentInterviewee(null);
            loadSessionInformationCard();
            break;
        case ATTRIBUTE: // attribute
            rubricsPane.getSelectionModel().select(attributesTab);
            break;
        case METRIC: // metrics
            rubricsPane.getSelectionModel().select(metricsTab);
            break;
        case QUESTION: // questions
            rubricsPane.getSelectionModel().select(questionsTab);
            break;
        default:
            break;
        }
    }

    /**
     * Scrolls the Transcript to the given index.
     *
     * @param index the index to scroll to.
     */
    public void scrollTo(int index) {
        remarkListPanel.scrollTo(index);
    }

    /**
     * Sets surrounding anchors for a Node if the Node is added to an AnchorPane.
     *
     * @param n Node of interest.
     * @param left Double value of left anchor. If value = Double.NaN, anchor is not set.
     * @param top Double value of top anchor. If value = Double.NaN, anchor is not set.
     * @param right Double value of right anchor. If value = Double.NaN, anchor is not set.
     * @param bottom Double value of bottom anchor. If value = Double.NaN, anchor is not set.
     */
    private void setAnchor(Node n, Double left, Double top, Double right, Double bottom) {
        if (!left.isNaN()) {
            AnchorPane.setLeftAnchor(n, left);
        }
        if (!right.isNaN()) {
            AnchorPane.setRightAnchor(n, right);
        }
        if (!top.isNaN()) {
            AnchorPane.setTopAnchor(n, top);
        }
        if (!bottom.isNaN()) {
            AnchorPane.setBottomAnchor(n, bottom);
        }
    }

}

