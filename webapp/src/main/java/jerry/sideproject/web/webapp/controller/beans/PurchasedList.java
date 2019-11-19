package jerry.sideproject.web.webapp.controller.beans;

import java.sql.Time;

public class PurchasedList {

    private ItemDtoList items;

    private Long id;

    private String location;

    private Time date;

    public ItemDtoList getItems() {
        return items;
    }

    public void setItems(ItemDtoList items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Time getDate() {
        return date;
    }

    public void setDate(Time date) {
        this.date = date;
    }
}
