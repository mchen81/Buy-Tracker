package jerry.sideproject.web.webapp.services;

import jerry.sideproject.web.webapp.controller.beans.ItemDtoList;
import jerry.sideproject.web.webapp.dao.interfaces.ItemDao;
import jerry.sideproject.web.webapp.services.interfaces.ShoppingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingHistoryServiceImpl implements ShoppingHistoryService {

    static Map<Long, ItemDtoList> shoppingHistory = new HashMap<>();

    @Autowired
    private ItemDao itemDao;

    @Override
    public List<ItemDtoList> findAll() {
        return new ArrayList<>(shoppingHistory.values());
    }

    @Override
    public Long addToHistory(ItemDtoList itemDtoList) {
        Long listId = getNextId();
        itemDtoList.setListId(listId);
        itemDtoList.setUpdated(true);
        shoppingHistory.put(listId, itemDtoList);
        return listId;
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
        if (shoppingHistory.containsKey(listId)) {
            ItemDtoList itemDtoList = getItemDtoListById(listId);
            if (editedItemDtoList.getLocation() == null) {
                editedItemDtoList.setLocation(itemDtoList.getLocation());
            }
            if (editedItemDtoList.getCreateDate() == null) {
                editedItemDtoList.setCreateDate(itemDtoList.getCreateDate());
            }
            shoppingHistory.put(listId, editedItemDtoList);
        } else {
            throw new IllegalArgumentException("list Id does not be contained in shopping history: " + listId);
        }
    }

    @Override
    public boolean isEmpty() {
        return shoppingHistory.size() == 0;
    }

    @Override
    public void updateData(Long listId) {

        ItemDtoList itemDtoList = shoppingHistory.get(listId);
        String createDate = itemDtoList.getCreateDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(createDate, formatter);
        Date date = Date.valueOf(localDate);


        //itemDao.insertOrReplaceItems(listId, itemDtoList.getItems());


    }

    /**
     * @return current max id + 1
     */
    private Long getNextId() {
        return (long) (shoppingHistory.size() + 1);
    }
}
