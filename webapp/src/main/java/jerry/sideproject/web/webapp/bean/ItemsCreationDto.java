package jerry.sideproject.web.webapp.bean;

import java.util.ArrayList;
import java.util.List;

public class ItemsCreationDto {

    private List<Item> items;

    public ItemsCreationDto() {
        this.items = new ArrayList<>();
    }

    public ItemsCreationDto(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}
