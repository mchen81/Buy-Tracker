package jerry.sideproject.web.webapp.services;

import jerry.sideproject.web.webapp.bean.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();

    void saveAll(List<Item> items);

    Double getTotalAmount();
}
