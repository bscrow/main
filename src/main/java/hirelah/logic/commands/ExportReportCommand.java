package hirelah.logic.commands;

import static hirelah.logic.commands.OpenReportCommand.MESSAGE_NOT_INTERVIEWED;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import hirelah.logic.commands.exceptions.CommandException;
import hirelah.model.Model;
import hirelah.model.hirelah.Attribute;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.IntervieweeList;
import hirelah.model.hirelah.Remark;
import hirelah.model.hirelah.exceptions.IllegalActionException;
import hirelah.storage.Storage;

import javafx.collections.ObservableList;
import javafx.util.Pair;

/**
 * Generates the report of the given interviewee in the form of a PDF.
 */
public class ExportReportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "Report of %s exported in %s with file name \"%d.pdf\".";
    public static final String MESSAGE_FORMAT = COMMAND_WORD + " <interviewee>";
    public static final String MESSAGE_FUNCTION =
            ": Exports the interview report of a particular interviewee in PDF.\n";
    public static final String MESSAGE_USAGE = MESSAGE_FORMAT
            + MESSAGE_FUNCTION
            + "Example: " + COMMAND_WORD
            + " Jane Doe ";

    private static final String ATTRIBUTE_SCORE_TITLE = "Attribute Score";
    private static final String REMARKS_TITLE = "Remarks";

    /**
     * Static class that holds information of sentences
     * to be printed in every line in the remarks part of the interviewee.
     */
    private static class TableRowEntry {
        private final String message;
        private final String duration;
        private final int valueYOffset;

        TableRowEntry(String message, String duration, int valueYOffset) {
            this.message = message;
            this.duration = duration;
            this.valueYOffset = valueYOffset;
        }

        public String getMessage() {
            return message;
        }

        public String getDuration() {
            return duration;
        }

        public int getValueYOffset() {
            return valueYOffset;
        }
    }

    private final String identifier;

    public ExportReportCommand(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNonNull(model);
        IntervieweeList interviewees = model.getIntervieweeList();
        Interviewee interviewee;
        String currentDirectory = System.getProperty("user.dir");
        int id = 0;
        try {
            interviewee = interviewees.getInterviewee(identifier);
            id = interviewee.getId();
            if (!interviewee.isInterviewed()) {
                throw new CommandException(String.format(MESSAGE_NOT_INTERVIEWED, interviewee));
            }
            model.setCurrentInterviewee(interviewee);

            Instant interviewTime = interviewee
                    .getTranscript()
                    .orElseThrow(() ->
                            new CommandException("The transcript for this interviewee could not be found"))
                    .getStartTime();
            Date myDate = Date.from(interviewTime);
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
            String formattedDate = formatter.format(myDate);

            ObservableList<Pair<Attribute, Double>> attributeToScoreData = interviewee
                    .getTranscript()
                    .orElseThrow(() ->
                            new CommandException("The transcript for this interviewee could not be found"))
                    .getAttributeToScoreData();

            ObservableList<Remark> remarks = interviewee
                    .getTranscript()
                    .orElseThrow(() ->
                            new CommandException("The transcript for this interviewee could not be found"))
                    .getRemarkListView();

            generateReport(interviewee.getId(), interviewee.getFullName().toUpperCase(),
                    formattedDate, attributeToScoreData, remarks);
        } catch (IllegalActionException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, interviewee, currentDirectory, id));
    }

    /**
     * Generate interview report in the form of PDF for a particular interviewee.
     *
     * @param id the ID of the interviewee
     * @param fullName the full name of the interviewee
     * @param date the date of the interview
     * @param attributeToScoreData the attribute to score mapping of the interviewee
     * @param remarks the remark list of this interviewee
     * @throws CommandException if the report could not be generated.
     */
    private void generateReport(
            int id, String fullName, String date, ObservableList<Pair<Attribute, Double>> attributeToScoreData,
            ObservableList<Remark> remarks) throws CommandException {
        try {
            PDDocument document = new PDDocument();
            Pair<Integer, PDPage> positionAfterNameAndAttributePrinting =
                    printNameAndAttributePart(document, fullName, attributeToScoreData);
            Pair<Integer, PDPage> startingPositionOfRemarksPart = positionAfterNameAndAttributePrinting;
            if (positionAfterNameAndAttributePrinting.getKey() > 780) {
                startingPositionOfRemarksPart = new Pair<>(0, new PDPage(PDRectangle.A4));
            }
            printRemarksPart(document, remarks, startingPositionOfRemarksPart);
            printDateAtEveryPage(document, date);
            document.save(id + ".pdf");
            document.close();
        } catch (IOException e) {
            throw new CommandException("There is an error in generating the report!");
        }
    }

    /**
     * Prints date stamp at the left corner of a page.
     *
     * @param document the current document
     * @param date the date to be printed
     * @throws IOException if there is an error in writing
     */
    private static void printDateAtEveryPage(PDDocument document, String date) throws IOException {
        for (PDPage page : document.getPages()) {
            PDPageContentStream.AppendMode append = PDPageContentStream.AppendMode.APPEND;
            PDPageContentStream pageContentStream =
                    new PDPageContentStream(document, page, append, true);
            pageContentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 9);
            pageContentStream.beginText();
            pageContentStream.newLineAtOffset(5, 826);
            pageContentStream.showText(date);
            pageContentStream.endText();
            pageContentStream.close();
        }
    }

    /**
     * Collate attribute scores mapping into one sentence and print them according to the available spaces per page.
     *
     * @param document the current document
     * @param fullName the full name of the interviewee
     * @param attributeScores all mappings between attribute and scores of this interviewee
     * @throws IOException if there is an error in writing
     */
    private static Pair<Integer, PDPage> printNameAndAttributePart(
            PDDocument document, String fullName, ObservableList<Pair<Attribute, Double>> attributeScores)
            throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair<Attribute, Double> attributeScorePair : attributeScores) {
            stringBuilder.append(attributeScorePair.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(attributeScorePair.getValue());
            if (attributeScorePair.equals(attributeScores.get(attributeScores.size() - 1))) {
                break;
            }
            stringBuilder.append(", ");
        }
        ArrayList<String> splitAttributeScoresSentence = splitSentence(stringBuilder.toString(), 80);
        ArrayList<String> splitName = splitSentence(fullName, 70);
        splitAttributeScoresSentence.add(0, ATTRIBUTE_SCORE_TITLE);
        splitAttributeScoresSentence.addAll(0, splitName);
        return generateNameAndAttributeAllPages(document, splitName.size(), splitAttributeScoresSentence);
    }

    /**
     * Generate all PDF pages of the name and attribute scores of the interviewee.
     *
     * @param document the current document
     * @param nameListSize the size of the name of the interviewee
     * @param nameAndAttributeScoreList all contents of the first part to be printed, including the name, attribute
     * score title and attribute score mapping
     * @throws IOException if there is an error in writing
     */
    private static Pair<Integer, PDPage> generateNameAndAttributeAllPages(
            PDDocument document, int nameListSize, ArrayList<String> nameAndAttributeScoreList) throws IOException {
        boolean firstPage = true;
        while (nameAndAttributeScoreList.size() > 57) {
            ArrayList<String> attributeScoreLisOnePage = new ArrayList<>();
            for (int i = 0; i < 57; i++) {
                attributeScoreLisOnePage.add(nameAndAttributeScoreList.get(0));
                nameAndAttributeScoreList.remove(0);
            }
            generateNameAndAttributeOnePage(document, firstPage ? nameListSize : 0, attributeScoreLisOnePage);
            firstPage = false;
        }
        int verticalSpacing = firstPage ? nameAndAttributeScoreList.size() * 13 + nameListSize + 40
                : nameAndAttributeScoreList.size() * 13 + 20;
        return new Pair<>(verticalSpacing,
                generateNameAndAttributeOnePage(document, firstPage ? nameListSize : 0, nameAndAttributeScoreList));
    }

    /**
     * Generate one PDF page of the name and attribute scores of the interviewee.
     *
     * @param document the current document
     * @param nameListSize the size of the name of the interviewee
     * @param nameAndAttributeScoreList all contents of the first part to be printed, including the name,
     * attribute score title and attribute score mapping
     * @throws IOException if there is an error in writing
     */
    private static PDPage generateNameAndAttributeOnePage(
            PDDocument document, int nameListSize, ArrayList<String> nameAndAttributeScoreList) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
        printName(page, pageContentStream, nameListSize, nameAndAttributeScoreList);
        printAttributeTitle(pageContentStream, nameAndAttributeScoreList);
        printAttributeScores(pageContentStream, nameAndAttributeScoreList);
        return page;
    }

    /**
     * Print the name part of the interviewee.
     *
     * @param pageContentStream the current page content stream
     * @param nameAndAttributeScoreList all contents of the first part to be printed, including the name,
     * attribute score title and attribute score mapping
     * @throws IOException if there is an error in writing
     */
    private static void printName(PDPage page, PDPageContentStream pageContentStream,
                                  int nameListSize, ArrayList<String> nameAndAttributeScoreList) throws IOException {
        PDFont font = PDType1Font.HELVETICA_BOLD_OBLIQUE;
        int fontSize = 15;
        pageContentStream.setFont(font, fontSize);
        pageContentStream.beginText();
        float alignCenterXStart = 0;
        pageContentStream.newLineAtOffset(0, 800);
        for (int i = 0; i < nameListSize; i++) {
            alignCenterXStart = (page.getMediaBox().getWidth()
                    - (font.getStringWidth(nameAndAttributeScoreList.get(0)) / 1000 * fontSize)) / 2;
            pageContentStream.newLineAtOffset(alignCenterXStart, 0);
            pageContentStream.showText(nameAndAttributeScoreList.get(0));
            nameAndAttributeScoreList.remove(0);
            pageContentStream.newLineAtOffset(-alignCenterXStart, -15);
        }
    }

    /**
     * Print the attribute score title.
     *
     * @param pageContentStream the current page content stream
     * @param attributeList all contents of the attribute score part, including title and mapping
     * @throws IOException if there is an error in writing
     */
    private static void printAttributeTitle(PDPageContentStream pageContentStream, ArrayList<String> attributeList)
            throws IOException {
        if (attributeList.get(0).equals(ATTRIBUTE_SCORE_TITLE)) {
            pageContentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 13);
            pageContentStream.newLineAtOffset(50, -15);
            pageContentStream.showText(attributeList.get(0));
            attributeList.remove(0);
            pageContentStream.newLineAtOffset(0, -15);
        } else {
            pageContentStream.newLineAtOffset(50, -15);
        }
    }

    /**
     * Print the attribute to score mapping of this interviewee.
     *
     * @param pageContentStream the current page content stream
     * @param attributeScoreList all mappings from attribute to score in the form of split sentence
     * @throws IOException if there is an error in writing
     */
    private static void printAttributeScores(
            PDPageContentStream pageContentStream, ArrayList<String> attributeScoreList) throws IOException {
        pageContentStream.setFont(PDType1Font.HELVETICA, 12);
        if (attributeScoreList.isEmpty()) {
            pageContentStream.newLineAtOffset(0, -20);
            pageContentStream.showText("-");
        }
        for (String currentString : attributeScoreList) {
            pageContentStream.showText(currentString);
            pageContentStream.newLineAtOffset(0, -13);
        }
        pageContentStream.endText();
        pageContentStream.close();
    }

    /**
     * Partition the remarks part to sufficient space of 1 A4 page and print them.
     *
     * @param document the current document
     * @param remarks the list of all remarks
     * @param startingPosition the starting position to print the remarks after printing the attribute scores
     * @throws IOException if there is an error in writing
     */
    private static void printRemarksPart(
            PDDocument document, ObservableList<Remark> remarks, Pair<Integer, PDPage> startingPosition)
            throws IOException {
        ArrayList<Remark> remarkList = new ArrayList<>();
        remarkList.addAll(remarks);
        int currentYOffset = 800 - startingPosition.getKey();
        boolean firstPage = true;
        ArrayList<TableRowEntry> rowsInAPage = new ArrayList<>();
        for (Remark currentRemark : remarkList) {
            ArrayList<String> splitRemarks = splitSentence(currentRemark.getMessage(), 73);
            boolean firstSentence = true;
            for (String currentLine : splitRemarks) {
                TableRowEntry currentRow = new TableRowEntry(currentLine,
                        firstSentence ? currentRemark.getTimeString() : "",
                        firstSentence ? -20 : -13);
                rowsInAPage.add(currentRow);
                currentYOffset -= (firstSentence ? 20 : 13);
                firstSentence = false;
                if (currentYOffset < 50) {
                    if (firstPage) {
                        generatePartialRemarkPage(document, startingPosition, rowsInAPage);
                        firstPage = false;
                    } else {
                        generateFullRemarkPage(document, rowsInAPage);
                    }
                    rowsInAPage = new ArrayList<>();
                    currentYOffset = 800;
                }
            }
            if (currentYOffset < 50) {
                if (firstPage) {
                    generatePartialRemarkPage(document, startingPosition, rowsInAPage);
                    firstPage = false;
                } else {
                    generateFullRemarkPage(document, rowsInAPage);
                }
                rowsInAPage = new ArrayList<>();
                currentYOffset = 800;
            }
        }
        if (firstPage) {
            generatePartialRemarkPage(document, startingPosition, rowsInAPage);
        } else {
            generateFullRemarkPage(document, rowsInAPage);
        }
    }

    /**
     * Generate partial remarks page, this is used to print remarks
     * that starts in the same page after the attribute scores part.
     *
     * @param document the current document
     * @param rowList the list of all rows
     * @throws IOException if there is an error in writing
     */
    private static void generatePartialRemarkPage(
            PDDocument document, Pair<Integer, PDPage> startingPosition, ArrayList<TableRowEntry> rowList)
            throws IOException {
        PDPageContentStream.AppendMode append = PDPageContentStream.AppendMode.APPEND;
        PDPageContentStream pageContentStream =
                new PDPageContentStream(document, startingPosition.getValue(), append, false);
        pageContentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 13);
        pageContentStream.beginText();
        pageContentStream.newLineAtOffset(50, 800 - startingPosition.getKey());
        pageContentStream.showText(REMARKS_TITLE);
        pageContentStream.newLineAtOffset(-50, 0);
        pageContentStream.setFont(PDType1Font.HELVETICA , 12);
        if (rowList.isEmpty()) {
            pageContentStream.newLineAtOffset(50, -20);
            pageContentStream.showText("-");
        }
        printRemarkPerLine(pageContentStream, rowList);
    }

    /**
     * Generate one whole page of remarks, this is used to print remarks that starts on a new page.
     *
     * @param document the current document
     * @param rowList the list of all rows
     * @throws IOException if there is an error in writing
     */
    private static void generateFullRemarkPage(PDDocument document, ArrayList<TableRowEntry> rowList)
            throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream pageContentStream = new PDPageContentStream(document, page);
        pageContentStream.setFont(PDType1Font.HELVETICA , 12);
        pageContentStream.beginText();
        pageContentStream.newLineAtOffset(0, 800);
        printRemarkPerLine(pageContentStream, rowList);
    }

    /**
     * Prints every row in the remarks part.
     *
     * @param pageContentStream the current page content stream
     * @param rowList the list of all rows
     * @throws IOException if there is an error in writing
     */
    private static void printRemarkPerLine(PDPageContentStream pageContentStream, ArrayList<TableRowEntry> rowList)
            throws IOException {
        for (TableRowEntry currentRow : rowList) {
            pageContentStream.newLineAtOffset(50, currentRow.getValueYOffset());
            pageContentStream.showText(currentRow.getDuration());
            pageContentStream.newLineAtOffset(50, 0);
            pageContentStream.showText(currentRow.getMessage());
            pageContentStream.newLineAtOffset(-100, 0);
        }
        pageContentStream.endText();
        pageContentStream.close();
    }


    /**
     * Splits the sentence to a maximum of certain characters per line.
     *
     * @param sentence the sentence to be splitted.
     * @param limit the maximum number of characters per line
     */
    private static ArrayList<String> splitSentence(String sentence, int limit) {
        ArrayList<String> splitRemarks = new ArrayList<>();
        String[] words = sentence.split(" ");

        int i = 0;
        while (true) {
            StringBuilder currentBuilder = new StringBuilder();
            if (i >= words.length) {
                break;
            }
            while (i < words.length) {
                String currentWord = words[i];
                if (currentWord.length() > limit) {
                    while (currentWord.length() > limit) {
                        int remainingCapacity = Math.min(limit - 1, limit - 1 - currentBuilder.length());
                        currentBuilder.append(currentWord.substring(0, remainingCapacity).toString() + "-");
                        splitRemarks.add(currentBuilder.toString());
                        currentWord = currentWord.substring(remainingCapacity);
                        currentBuilder = new StringBuilder();
                    }
                    currentBuilder = new StringBuilder();
                    currentBuilder.append(currentWord + " ");
                    currentWord = words[++i];
                }
                if (currentBuilder.length() + currentWord.length() > limit) {
                    break;
                }
                currentBuilder.append(currentWord + " ");
                i++;
            }
            splitRemarks.add(currentBuilder.toString());
        }
        return splitRemarks;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportReportCommand // instanceof handles nulls
                && identifier.equals(((ExportReportCommand) other).identifier));
    }
}
