package jerry.sideproject.web.webapp.services.interfaces;

import jerry.sideproject.web.webapp.controller.beans.ItemDtoList;

import java.util.List;

public interface ShoppingHistoryService {

    void initialize();

    List<ItemDtoList> findAll();

    void addList(ItemDtoList itemDtoList);


}
