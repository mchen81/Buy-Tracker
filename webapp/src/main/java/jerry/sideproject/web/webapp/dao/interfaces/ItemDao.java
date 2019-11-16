package jerry.sideproject.web.webapp.dao.interfaces;

import jerry.sideproject.web.webapp.bean.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao {

    List<Item> getItems(long buyingListId) throws SQLException;

    int insertItems(long buyingListId, List<Item> items) throws SQLException;

    int replaceItems(long buyingListId, List<Item> items) throws SQLException;

}
