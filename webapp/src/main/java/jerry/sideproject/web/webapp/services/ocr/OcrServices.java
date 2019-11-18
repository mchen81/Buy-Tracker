package jerry.sideproject.web.webapp.services.ocr;

import jerry.sideproject.web.webapp.controller.beans.ItemDto;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Process the text from OCR result
 */
@Service
public class OcrServices {

    /**
     * The directory storing language training data
     */
    private static final String TESSERACT_DATA_PATH = "src/main/resources/tessdata/";

    /**
     * Recoognize an image and parse it to the buying list
     *
     * @param imagePath a image path
     */
    public List<ItemDto> imageRecognize(Path imagePath) {
        List<ItemDto> result = new ArrayList<>();
        Tesseract tesseract = new Tesseract();
        String text = "";
        try {
            tesseract.setDatapath(TESSERACT_DATA_PATH);
            text = tesseract.doOCR(new File(imagePath.toString())); // to OCR
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        String[] lines = text.split("\n");
        long count = 1;
        for (String line : lines) {
            ItemDto itemDto = splitString(line);
            if (!itemDto.isEmpty()) {
                itemDto.setId(count);
                result.add(itemDto);
            }
            count++;
        }
        return result;
    }

    /**
     * split one line of receipt to buying item
     *
     * @param line one line on receipt
     * @return an Item
     */
    private ItemDto splitString(String line) {
        ItemDto itemDto = new ItemDto();
        String[] words = line.split(" "); // to get the last element(price)
        try {
            itemDto.setPrice(words[words.length - 1]);
        } catch (NumberFormatException e) {
            itemDto.setPrice("0");
        }
        String itemName = "";
        for (int i = 0; i < words.length - 2; i++) {
            String word = words[i];
            itemName += (word + " "); // except for the last, put to items
        }
        itemDto.setName(itemName.trim());
        return itemDto;
    }
}
