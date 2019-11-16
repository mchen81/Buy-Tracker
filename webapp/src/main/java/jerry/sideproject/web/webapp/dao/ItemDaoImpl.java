package jerry.sideproject.web.webapp.dao;

import jerry.sideproject.web.webapp.bean.Item;
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

    private static final String SQL_QUERY_GET_ITEMS = "{Call GET_ITEMS(?)}";

    private static final String SQL_QUERY_INS_ITEMS = "{Call INS_ITEMS(?,?,?,?)}"; // listId, name , price , category

    private static final String SQL_QUERY_REP_ITEMS = "{Call REPLACE_ITEMS(?,?,?,?)}";

    @Override
    public List<Item> getItems(long buyingListId) throws SQLException {

        Connection con = null;
        con = dataSource.getConnection();
        List<Item> items = new ArrayList<>();
        CallableStatement callableStatement = con.prepareCall((SQL_QUERY_GET_ITEMS));
        callableStatement.setLong("i_BUYING_LIST_ID", buyingListId);
        ResultSet resultSet = callableStatement.executeQuery();
        con.close();
        while (resultSet.next()) {
            Item item = new Item();
            item.setId(resultSet.getLong("ID"));
            item.setName(resultSet.getString("ITEM_NAME"));
            item.setPrice(resultSet.getDouble("ITEM_PRICE"));
            item.setCategory(resultSet.getString("CATEGORY"));
            items.add(item);
        }
        return items;
    }

    @Override
    public int insertItems(long buyingListId, List<Item> items) throws SQLException {

        Connection con = null;
        con = dataSource.getConnection();
        CallableStatement callableStatement = con.prepareCall((SQL_QUERY_INS_ITEMS));
        for (Item item : items) {
            callableStatement.setLong("i_BUYING_LIST_ID", buyingListId);
            callableStatement.setString("i_ITEM_NAME", item.getName());
            callableStatement.setDouble("i_ITEM_PRICE", item.getPrice());
            callableStatement.setString("i_CATEGORY", item.getCategory());
            callableStatement.addBatch();
        }
        callableStatement.executeBatch();
        con.close();

        return 0;
    }

    @Override
    public int replaceItems(long buyingListId, List<Item> items) throws SQLException {

        Connection con = null;
        con = dataSource.getConnection();
        CallableStatement callableStatement = con.prepareCall((SQL_QUERY_REP_ITEMS));
        for (Item item : items) {
            callableStatement.setLong("i_BUYING_LIST_ID", buyingListId);
            callableStatement.setString("i_ITEM_NAME", item.getName());
            callableStatement.setDouble("i_ITEM_PRICE", item.getPrice());
            callableStatement.setString("i_CATEGORY", item.getCategory());
            callableStatement.addBatch();
        }
        callableStatement.executeBatch();
        con.close();

        return 0;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
