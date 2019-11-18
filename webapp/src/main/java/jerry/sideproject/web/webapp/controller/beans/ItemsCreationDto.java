package jerry.sideproject.web.webapp.controller.beans;

import java.util.ArrayList;
import java.util.List;

public class ItemsCreationDto {

    private List<ItemDto> itemDtos;

    public ItemsCreationDto() {
        this.itemDtos = new ArrayList<>();
    }

    public ItemsCreationDto(List<ItemDto> itemDtos) {
        this.itemDtos = itemDtos;
    }

    public List<ItemDto> getItemDtos() {
        return itemDtos;
    }

    public void setItemDtos(List<ItemDto> itemDtos) {
        this.itemDtos = itemDtos;
    }

    public void addItem(ItemDto itemDto) {
        this.itemDtos.add(itemDto);
    }
}
