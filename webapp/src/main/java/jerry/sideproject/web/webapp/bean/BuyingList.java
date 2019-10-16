package jerry.sideproject.web.webapp.bean;

import java.util.ArrayList;
import java.util.List;

public class BuyingList {

    private List<Item> items;

    private Double totalAmount;

    public BuyingList() {
        this.items = new ArrayList<>();
        this.totalAmount = Double.NaN;
    }

    /**
     * add item to items
     *
     * @param item Item
     */
    public void addItem(Item item) {
        if (null != item && !item.isEmpty()) {
            items.add(item);
        }
    }

    /**
     * get total amount in buying list
     *
     * @return Total Spending in the list
     */
    public Double getTotalAmount() {
        totalAmount = Double.NaN;
        for (Item i : items) {
            totalAmount += i.getPrice();
        }
        return totalAmount;
    }
}
