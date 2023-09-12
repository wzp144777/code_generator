package study.pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import technology.tabula.*;
import technology.tabula.detectors.NurminenDetectionAlgorithm;
import technology.tabula.extractors.BasicExtractionAlgorithm;
import technology.tabula.extractors.ExtractionAlgorithm;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfParseUtil {

    /**
     * tabula解析pdf文件内容 内涵表格
     * @param path
     * @throws IOException
     */
    public static void testPdfParse(String path) throws IOException {
        PDDocument document = null;
        try{
            File in = new File(path);
            document = PDDocument.load(in);

            // pdfbox读取pdf内容
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String text = pdfTextStripper.getText(document);
            System.err.println(text);

            List<Table> table = extractTablesFromPDF(document);

        }catch(Exception e) {
            e.printStackTrace();
        }finally {

            document.close();
        }
    }

    /**
     * tabula 基于pdfbox的二次开发  实现获取pdf表格  pdfbox版本2.0
     * @param document
     * @return
     */
    public static List<Table> extractTablesFromPDF(PDDocument document) {
        NurminenDetectionAlgorithm detectionAlgorithm = new NurminenDetectionAlgorithm();

        ExtractionAlgorithm algExtractor;

        SpreadsheetExtractionAlgorithm extractor=new SpreadsheetExtractionAlgorithm();

        ObjectExtractor extractor1 = new ObjectExtractor(document);
        PageIterator pages = extractor1.extract();
        List<Table> tables=new ArrayList<Table>();
        while (pages.hasNext()) {
            Page page = pages.next();
            if (extractor.isTabular(page)) {
                algExtractor=new SpreadsheetExtractionAlgorithm();
            }
            else
                algExtractor=new BasicExtractionAlgorithm();

            List<Rectangle> tablesOnPage = detectionAlgorithm.detect(page);


            for (Rectangle guessRect : tablesOnPage) {
                Page guess = page.getArea(guessRect);
                tables.addAll((List<Table>) algExtractor.extract(guess));
            }

        }

        return tables;
    }

    /**
     *
     * @param path
     */
    public static void testPdfParse1(String path) {
        try{
            File in = new File(path);
            PdfReader reader = null;
            StringBuffer buff = new StringBuffer();
            try {
                reader = new PdfReader(path);
                PdfReaderContentParser parser = new PdfReaderContentParser(reader);
                int num = reader.getNumberOfPages();// 获得页数
//                Rectangle rect = new Rectangle(0, 0, 490, 580);
//                RenderFilter filter = new RegionTextRenderFilter(rect);
//                TextExtractionStrategy strategy;
//                for (int i = 1; i <= reader.getNumberOfPages(); i++) {
//                    strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
////                    out.println(PdfTextExtractor.getTextFromPage(reader, i, strategy));
//                        buff.append(PdfTextExtractor.getTextFromPage(reader, i, strategy));
//                }
                TextExtractionStrategy strategy;
                for (int i = 1; i <= num; i++) {
                    strategy = parser.processContent(i, new SimpleTextExtractionStrategy());

                    buff.append(strategy.getResultantText());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.err.println(buff.toString());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        PdfParseUtil.testPdfParse("C");
//        PdfParseUtil.testPdfParse1("C");
    }
}



