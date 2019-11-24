package jerry.sideproject.web.webapp.dao.interfaces;

import jerry.sideproject.web.webapp.controller.beans.ItemDtoList;

import java.sql.SQLException;
import java.util.List;

public interface ShoppingHistoryDao {

    void updateHistory(ItemDtoList newItemList, Long listId) throws SQLException;

    List<ItemDtoList> getHisTory() throws SQLException;

}
