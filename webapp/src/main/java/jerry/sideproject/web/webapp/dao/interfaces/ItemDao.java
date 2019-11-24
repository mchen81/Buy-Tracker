package jerry.sideproject.web.webapp.dao.interfaces;

import jerry.sideproject.web.webapp.dao.beans.ShoppingListDo;
import jerry.sideproject.web.webapp.dao.beans.ItemDo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ItemDao {

    /**
     * Obtain the item list by buying list id
     *
     * @param shoppingListId
     * @return
     * @throws SQLException
     */
    List<ItemDo> getItemsByListId(Long shoppingListId) throws SQLException;


    void insertItems(List<ItemDo> itemDoList, Long listId) throws SQLException;


    void replaceItems(List<ItemDo> itemDoList, Long listId) throws SQLException;

    void deleteItemsByListId(Long listId) throws SQLException;

    Map<Long, String > getCategoriesMap() throws SQLException;

}
