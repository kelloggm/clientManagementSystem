package lv.javaguru.cms.services.course.bills;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TextReplacementService {

    public void replace(XWPFDocument document,
                        String searchText,
                        String replacement) {
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            replace(paragraph, searchText, replacement);
        }
        iterateThroughTables(document, searchText, replacement);
    }

    private static void iterateThroughTables(XWPFDocument doc,
                                             String searchText,
                                             String replacement) {
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        replace(paragraph, searchText, replacement);
                    }
                }
            }
        }
    }

    public static void replace(XWPFParagraph paragraph,
                               String searchText,
                               String replacement) {
        boolean found = true;
        while (found) {
            found = false;
            int pos = paragraph.getText().indexOf(searchText);
            if (pos >= 0) {
                found = true;
                Map<Integer, XWPFRun> posToRuns = getPosToRuns(paragraph);
                XWPFRun run = posToRuns.get(pos);
                XWPFRun lastRun = posToRuns.get(pos + searchText.length() - 1);
                int runNum = paragraph.getRuns().indexOf(run);
                int lastRunNum = paragraph.getRuns().indexOf(lastRun);
                String texts[] = replacement.split("\n");
                run.setText(texts[0], 0);
                XWPFRun newRun = run;
                for (int i = 1; i < texts.length; i++) {
                    newRun.addCarriageReturn();
                    newRun = paragraph.insertNewRun(runNum + i);
                /*
                    We should copy all style attributes
                    to the newRun from run
                    also from background color, ...
                    Here we duplicate only the simple attributes...
                 */
                    newRun.setText(texts[i]);
                    newRun.setBold(run.isBold());
                    newRun.setCapitalized(run.isCapitalized());
                    // newRun.setCharacterSpacing(run.getCharacterSpacing());
                    newRun.setColor(run.getColor());
                    newRun.setDoubleStrikethrough(run.isDoubleStrikeThrough());
                    newRun.setEmbossed(run.isEmbossed());
                    newRun.setFontFamily(run.getFontFamily());
                    newRun.setFontSize(run.getFontSize());
                    newRun.setImprinted(run.isImprinted());
                    newRun.setItalic(run.isItalic());
                    newRun.setKerning(run.getKerning());
                    newRun.setShadow(run.isShadowed());
                    newRun.setSmallCaps(run.isSmallCaps());
                    newRun.setStrikeThrough(run.isStrikeThrough());
                    newRun.setSubscript(run.getSubscript());
                    newRun.setUnderline(run.getUnderline());
                }
                for (int i = lastRunNum + texts.length - 1; i > runNum + texts.length - 1; i--) {
                    paragraph.removeRun(i);
                }
            }
        }
    }

    private static Map<Integer, XWPFRun> getPosToRuns(XWPFParagraph paragraph) {
        int pos = 0;
        Map<Integer, XWPFRun> map = new HashMap<Integer, XWPFRun>(10);
        for (XWPFRun run : paragraph.getRuns()) {
            String runText = run.text();
            if (runText != null) {
                for (int i = 0; i < runText.length(); i++) {
                    map.put(pos + i, run);
                }
                pos += runText.length();
            }
        }
        return (map);
    }

}
