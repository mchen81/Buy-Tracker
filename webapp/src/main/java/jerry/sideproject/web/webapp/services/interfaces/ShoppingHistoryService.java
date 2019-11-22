package jerry.sideproject.web.webapp.services.interfaces;

import jerry.sideproject.web.webapp.controller.beans.ItemDtoList;

import java.util.List;

public interface ShoppingHistoryService {

    List<ItemDtoList> findAll();

    Long addToHistory(ItemDtoList itemDtoList);

    ItemDtoList getItemDtoListById(Long listId);

    void removeItemDtoList(Long listId);

    void editItemDtoList(Long listId, ItemDtoList editedItemDtoList);

    boolean isEmpty();

    void updateData(Long listId);
}
