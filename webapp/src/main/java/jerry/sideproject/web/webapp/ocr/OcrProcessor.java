package jerry.sideproject.web.webapp.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OcrProcessor {

    private static final String TESSERACT_DATA_PATH = "src/main/resources/tessdata/";

    public static String imageRecognize(String imagePath) {
        Tesseract tesseract = new Tesseract();
        String text = "";
        try {
            tesseract.setDatapath(TESSERACT_DATA_PATH);
            text = tesseract.doOCR(new File(imagePath));
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return text;
    }
}
