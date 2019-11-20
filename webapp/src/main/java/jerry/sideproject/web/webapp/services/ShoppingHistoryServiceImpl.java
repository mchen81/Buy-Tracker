package jerry.sideproject.web.webapp.services;

import jerry.sideproject.web.webapp.controller.beans.ItemDto;
import jerry.sideproject.web.webapp.controller.beans.ItemDtoList;
import jerry.sideproject.web.webapp.controller.beans.ShoppingHistory;
import jerry.sideproject.web.webapp.services.interfaces.ShoppingHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShoppingHistoryServiceImpl implements ShoppingHistoryService {

    static ShoppingHistory shoppingHistory = new ShoppingHistory();

    @Override
    public void initialize() {
        ItemDtoList itemDtoList = new ItemDtoList();
        ItemDto itemDto = new ItemDto();
        itemDto.setName("apple");
        itemDto.setPrice(1D);
        itemDto.setId(1);
        itemDtoList.addItem(itemDto);
        shoppingHistory.addItemList(itemDtoList);
    }

    @Override
    public List<ItemDtoList> findAll() {
        return shoppingHistory.getItemDtoLists();
    }

    @Override
    public void addList(ItemDtoList itemDtoList) {
        shoppingHistory.addItemList(itemDtoList);
    }


}
