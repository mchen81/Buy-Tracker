package jerry.sideproject.web.webapp.controller.beans;

import java.util.ArrayList;
import java.util.List;

public class ItemDtoList {

    private List<ItemDto> items;

    private Long listId;

    private String location;

    private String createDate;

    private boolean isUpdated;

    public ItemDtoList() {
        this.items = new ArrayList<>();
        isUpdated = false;
    }

    public ItemDtoList(List<ItemDto> items) {
        this.items = items;
        isUpdated = false;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
        isUpdated = false;
    }

    public void addItem(ItemDto itemDto) {
        this.items.add(itemDto);
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
        isUpdated = false;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        isUpdated = false;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
        isUpdated = false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("List Id: ");
        stringBuilder.append(listId);
        stringBuilder.append("\n");

        for (ItemDto itemDto : items) {
            stringBuilder.append("Item name: ");
            stringBuilder.append(itemDto.getName());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
