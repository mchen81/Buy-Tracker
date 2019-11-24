package jerry.sideproject.web.webapp.controller.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ItemDtoList {

    private List<ItemDto> items;

    private Long listId;

    private String location;

    private Date createDate;

    private boolean isUpdated;

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public ItemDtoList() {
        this.items = new ArrayList<>();
        isUpdated = false;
    }

    public ItemDtoList(List<ItemDto> items) {
        this.items = items;
        isUpdated = false;
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
        isUpdated = false;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        if (null != listId && !listId.equals(this.listId)) {
            isUpdated = false;
            this.listId = listId;
        }
    }

    public String getLocation() {
        return location;
    }

    public Date getSqlDate() {
        return this.createDate;
    }

    public void setLocation(String location) {
        if (null != location && !location.equals(this.location)) {
            isUpdated = false;
            this.location = location;
        }
    }

    public String getCreateDate() {
        if (createDate == null) {
            return null;
        }
        return createDate.toString();
    }

    public void setCreateDate(String createDate) {
        if (this.createDate == null && createDate != null) {
            this.createDate = Date.valueOf(createDate);
            isUpdated = false;
            return;
        }
        if (createDate != null && !createDate.equals(this.createDate.toString())) {
            this.createDate = Date.valueOf(createDate);
            isUpdated = false;
        }
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
