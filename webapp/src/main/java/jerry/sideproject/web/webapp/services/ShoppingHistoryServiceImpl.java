package jerry.sideproject.web.webapp.services;

import jerry.sideproject.web.webapp.controller.beans.ItemDtoList;
import jerry.sideproject.web.webapp.services.interfaces.ShoppingHistoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingHistoryServiceImpl implements ShoppingHistoryService {

    static Map<Long, ItemDtoList> shoppingHistory = new HashMap<>();

    @Override
    public List<ItemDtoList> findAll() {
        return new ArrayList<>(shoppingHistory.values());
    }

    @Override
    public void addToHistory(ItemDtoList itemDtoList) {
        Long listId = getNextId();
        itemDtoList.setListId(listId);
        shoppingHistory.put(listId, itemDtoList);

    }

    @Override
    public ItemDtoList getItemDtoListById(Long listId) {
        return shoppingHistory.get(listId);
    }

    @Override
    public void removeItemDtoList(Long listId) {
        shoppingHistory.remove(listId);
    }

    @Override
    public void editItemDtoList(Long listId, ItemDtoList editedItemDtoList) {
        ItemDtoList itemDtoList = getItemDtoListById(listId);
        if (editedItemDtoList.getLocation() == null) {
            editedItemDtoList.setLocation(itemDtoList.getLocation());
        }
        if (editedItemDtoList.getCreateDate() == null) {
            editedItemDtoList.setCreateDate(itemDtoList.getCreateDate());
        }
        shoppingHistory.put(listId, editedItemDtoList);
    }

    @Override
    public boolean isEmpty() {
        return shoppingHistory.size() == 0;
    }


    /**
     * @return current max id + 1
     */
    private Long getNextId() {
        return (long) (shoppingHistory.size() + 1);
    }

}
