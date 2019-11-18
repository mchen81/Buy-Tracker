package jerry.sideproject.web.webapp.dao;

import jerry.sideproject.web.webapp.dao.beans.BuyingListDo;
import jerry.sideproject.web.webapp.dao.beans.ItemDo;
import jerry.sideproject.web.webapp.dao.interfaces.ItemDao;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    private DataSource dataSource;

    private static final String SQL_GET_BUYING_LISTS = "{CALL GET_BUY_LISTS(?)}";

    private static final String SQL_INSERT_INS_BUY_LIST = "{CALL INS_BUY_LIST(?,?,?)}";

    private static final String SQL_QUERY_GET_ITEMS = "{Call GET_ITEMS(?)}";

    private static final String SQL_INSERT_INS_REP_ITEMS = "{Call INS_ITEMS(?,?,?,?)}"; // listId, name , price , category

    @Override
    public List<ItemDo> getItemList(long buyingListId) throws SQLException {
        Connection con = dataSource.getConnection();
        List<ItemDo> itemDos = new ArrayList<>();
        CallableStatement callableStatement = con.prepareCall((SQL_QUERY_GET_ITEMS));
        callableStatement.setLong("i_BUYING_LIST_ID", buyingListId);
        try {
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ItemDo itemDo = new ItemDo();
                itemDo.setId(resultSet.getLong("ID"));
                itemDo.setName(resultSet.getString("NAME"));
                itemDo.setPrice(resultSet.getDouble("PRICE"));
                itemDo.setCategoryId(resultSet.getLong("CATEGORY_ID"));
                itemDos.add(itemDo);
            }
            return itemDos;
        } finally {
            con.close();
        }
    }

    @Override
    public long insertBuyingList(BuyingListDo buyingListDo) throws SQLException {
        Connection con = dataSource.getConnection();
        CallableStatement callableStatement = con.prepareCall((SQL_INSERT_INS_BUY_LIST));
        callableStatement.setLong("i_USER_ID", buyingListDo.getUserId());
        callableStatement.setTime("i_TIME", buyingListDo.getTime());
        callableStatement.setString("i_LOCATION", buyingListDo.getLocation());
        try {
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                return 0;
            }
        } finally {
            con.close();
        }
    }

    @Override
    public List<BuyingListDo> getBuyingList(long userId) throws SQLException {
        Connection con = dataSource.getConnection();
        List<BuyingListDo> result = new ArrayList<>();
        CallableStatement callableStatement = con.prepareCall((SQL_GET_BUYING_LISTS));
        callableStatement.setLong("i_USER_ID", userId);
        try {
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                BuyingListDo buyingListDo = new BuyingListDo();
                buyingListDo.setId(resultSet.getLong("ID"));
                buyingListDo.setUserId(userId);
                buyingListDo.setLocation(resultSet.getString("LOCATION"));
                buyingListDo.setTime(resultSet.getTime("TIME"));
            }
            return result;
        } finally {
            con.close();
        }
    }

    @Override
    public int insertOrReplaceItems(long buyingListId, List<ItemDo> itemDos) throws SQLException {
        Connection con = dataSource.getConnection();
        CallableStatement callableStatement = con.prepareCall((SQL_INSERT_INS_REP_ITEMS));
        try {
            for (ItemDo itemDo : itemDos) {
                callableStatement.setLong("i_BUYING_LIST_ID", buyingListId);
                callableStatement.setString("i_ITEM_NAME", itemDo.getName());
                callableStatement.setDouble("i_ITEM_PRICE", itemDo.getPrice());
                callableStatement.setLong("i_CATEGORY_ID", itemDo.getCategoryId());
                callableStatement.addBatch();
            }
            callableStatement.executeBatch();
            return 0;
        } finally {
            con.close();
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
