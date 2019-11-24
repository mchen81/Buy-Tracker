package jerry.sideproject.web.webapp.dao;

import jerry.sideproject.web.webapp.dao.beans.ItemDo;
import jerry.sideproject.web.webapp.dao.interfaces.ItemDao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemDaoImpl implements ItemDao {

    private DataSource dataSource;

    private static final String SQL_GET_ITEMS_BY_LIST_ID = "{CALL getItemsByShoppingListId(?)}";

    private static final String SQL_INSERT_ITEM = "{CALL insertItem(?,?,?,?)}";

    private static final String SQL_CLEAR_ITEMS = "{CALL clearItemsByListId(?)}";

    private static final String SQL_GET_CATEGORIES = "{CALL getCategories()}";

    @Override
    public List<ItemDo> getItemsByListId(Long shoppingListId) throws SQLException {
        Connection con = dataSource.getConnection();
        List<ItemDo> itemDos = new ArrayList<>();
        CallableStatement callableStatement = con.prepareCall((SQL_GET_ITEMS_BY_LIST_ID));
        callableStatement.setLong("I_SHOPPING_LIST_ID", shoppingListId);
        try {
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ItemDo itemDo = new ItemDo();
                itemDo.setShoppingListId(shoppingListId);
                itemDo.setCategory(resultSet.getString("CATEGORY"));
                itemDo.setName(resultSet.getString("ITEM_NAME"));
                itemDo.setPrice(resultSet.getDouble("ITEM_PRICE"));
                itemDos.add(itemDo);
            }
            return itemDos;
        } finally {
            con.close();
        }
    }

    @Override
    public void insertItems(List<ItemDo> itemDoList, Long listId) throws SQLException {
        Connection con = dataSource.getConnection();
        CallableStatement callableStatement = con.prepareCall((SQL_INSERT_ITEM));
        try {
            for (ItemDo itemDo : itemDoList) {
                callableStatement.setLong("i_list_id", listId);
                callableStatement.setString("i_category", itemDo.getCategory());
                callableStatement.setString("i_name", itemDo.getName());
                callableStatement.setDouble("i_price", itemDo.getPrice());
                callableStatement.addBatch();
            }
            callableStatement.executeBatch();
        } finally {
            con.close();
        }
    }

    @Override
    public void replaceItems(List<ItemDo> itemDoList, Long listId) throws SQLException {
        deleteItemsByListId(listId);
        insertItems(itemDoList, listId);
    }

    @Override
    public void deleteItemsByListId(Long listId) throws SQLException {
        Connection con = dataSource.getConnection();
        CallableStatement callableStatement = con.prepareCall(SQL_CLEAR_ITEMS);
        try {
            callableStatement.setLong("I_LIST_ID", listId);
        } finally {
            con.close();
        }
    }

    @Override
    public Map<Long, String> getCategoriesMap() throws SQLException {
        Connection con = dataSource.getConnection();
        CallableStatement callableStatement = con.prepareCall(SQL_GET_CATEGORIES);
        Map<Long, String> categoriesMap = new HashMap<>();
        try {
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                categoriesMap.put(resultSet.getLong("ID"), resultSet.getString("NAME"));
            }
        } finally {
            con.close();
        }
        return categoriesMap;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
