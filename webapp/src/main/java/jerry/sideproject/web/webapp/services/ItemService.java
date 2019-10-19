package jerry.sideproject.web.webapp.services;

import jerry.sideproject.web.webapp.bean.Item;

import java.util.List;

/**
 * Service for process items
 */
public interface ItemService {

    /**
     * @return current item list
     */
    List<Item> findAll();

    /**
     * give a list of items, store it in a map
     *
     * @param items a list of Item
     */
    void saveAll(List<Item> items);

    /**
     * @return total spending of current items
     */
    Double getTotalAmount();
}
