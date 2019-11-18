package jerry.sideproject.web.webapp.dao.interfaces;

import jerry.sideproject.web.webapp.dao.beans.BuyingListDo;
import jerry.sideproject.web.webapp.dao.beans.ItemDo;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao {

    /**
     * Obtain the item list by buying list id
     *
     * @param buyingListId
     * @return
     * @throws SQLException
     */
    List<ItemDo> getItemList(long buyingListId) throws SQLException;

    /**
     * insert a buying list
     *
     * @param buyingListDo
     * @return the buying list's id
     * @throws SQLException
     */
    long insertBuyingList(BuyingListDo buyingListDo) throws SQLException;

    /**
     * get an user's buying list
     *
     * @param userId an user's id
     * @return a list of buying list
     * @throws SQLException
     */
    List<BuyingListDo> getBuyingList(long userId) throws SQLException;

    /**
     * if a buying list has been existing in DB, replace it. Otherwise do insertion.
     *
     * @param buyingListId
     * @param itemDos
     * @return
     * @throws SQLException
     */
    int insertOrReplaceItems(long buyingListId, List<ItemDo> itemDos) throws SQLException;
}
