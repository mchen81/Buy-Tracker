package jerry.sideproject.web.webapp.services.interfaces;

import jerry.sideproject.web.webapp.controller.beans.ItemDto;

import java.util.List;

/**
 * Service for process items
 */
public interface ItemService {

    /**
     * @return current item list
     */
    List<ItemDto> findAll();

    /**
     * give a list of items, store it in a map
     *
     * @param itemDtos a list of Item
     */
    void saveAll(List<ItemDto> itemDtos);

    /**
     * @return total spending of current items
     */
    Double getTotalAmount();

    /**
     * reset items data
     */
    void clear();

    /**
     * remove an item by id
     * @param Id item's id
     */
    void remove(Long Id);


}
