package jerry.sideproject.web.webapp.controller.beans;

import java.util.ArrayList;
import java.util.List;

public class ItemDtoList {

    private List<ItemDto> items;

    private long listId;

    private String location;

    private String createDate;


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

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
