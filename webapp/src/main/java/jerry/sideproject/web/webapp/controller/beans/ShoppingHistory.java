package jerry.sideproject.web.webapp.controller.beans;

import java.util.ArrayList;
import java.util.List;

public class ShoppingHistory {

    private List<ItemDtoList> itemDtoLists = new ArrayList<>();

    public List<ItemDtoList> getItemDtoLists() {
        return itemDtoLists;
    }

    public void setItemDtoLists(List<ItemDtoList> itemDtoLists) {
        this.itemDtoLists = itemDtoLists;
    }

    public void addItemList(ItemDtoList itemDtoList) {
        itemDtoLists.add(itemDtoList);
    }

}
