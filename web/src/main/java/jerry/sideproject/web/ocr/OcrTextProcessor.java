package jerry.sideproject.web.ocr;

import jerry.sideproject.web.bean.BuyingList;
import jerry.sideproject.web.bean.Item;

/**
 * Process the text from OCR result
 */
public class OcrTextProcessor {

    private String ocrText;
    private BuyingList buyingList;

    public OcrTextProcessor(String ocrText) {
        this.ocrText = ocrText;
        buyingList = new BuyingList();
    }

    /**
     * process the ocrText into buying list
     */
    private void process() {
        String[] lines = ocrText.split("\n");
        for (String line : lines) {
            Item item = splitString(line);
            if (!item.isEmpty()) {
                buyingList.addItem(item);
            }
        }
    }

    /**
     * split one line of receipt to buying item
     *
     * @param line one line on receipt
     * @return an Item
     */
    private Item splitString(String line) {
        Item item = new Item();
        String[] words = line.split(" "); // to get the last element(price)
        try {
            item.setPrice(Double.valueOf(words[words.length - 1]));
        } catch (NumberFormatException e) {
            item.setPrice(Double.NaN);
        }
        String itemName = "";
        for (int i = 0; i < words.length - 2; i++) {
            String word = words[i];
            itemName += (word + " "); // except for the last, put to items
        }
        item.setName(itemName.trim());
        return item;
    }
}
