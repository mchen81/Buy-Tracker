package jerry.sideproject.web.webapp.controller.beans;

import java.util.ArrayList;
import java.util.List;

public class ItemDtoList {

    private List<ItemDto> items;

    public ItemDtoList() {
        this.items = new ArrayList<>();
    }

    public ItemDtoList(List<ItemDto> items) {
        this.items = items;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public void addItem(ItemDto itemDto) {
        this.items.add(itemDto);
    }
}
