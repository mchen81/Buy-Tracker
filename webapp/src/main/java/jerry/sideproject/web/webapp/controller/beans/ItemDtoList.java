package jerry.sideproject.web.webapp.controller.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ItemDtoList {

    private List<ItemDto> items;

    private Long listId;

    private String location;

    private Date createDate;


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

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public String getLocation() {
        return location;
    }

    public Date getSqlDate() {
        return this.createDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateDate() {
        return createDate.toString();
    }

    public void setCreateDate(String createDate) {
        this.createDate = Date.valueOf(createDate);
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
