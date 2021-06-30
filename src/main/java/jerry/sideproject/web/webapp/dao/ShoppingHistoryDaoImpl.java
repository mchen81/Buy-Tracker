package jerry.sideproject.web.webapp.dao;

import jerry.sideproject.web.webapp.controller.beans.ItemDtoList;
import jerry.sideproject.web.webapp.dao.interfaces.ShoppingHistoryDao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShoppingHistoryDaoImpl implements ShoppingHistoryDao {

    private DataSource dataSource;

    private static final String SQL_UPDATE_HISTORY = "{CALL insertOrUpdateHistory(?,?,?)}";

    private static final String SQL_GET_HISTORY = "{CALL getHistory()}";

    @Override
    public void updateHistory(ItemDtoList newItemList, Long listId) throws SQLException {
        Connection con = dataSource.getConnection();
        try {
            CallableStatement callableStatement = con.prepareCall((SQL_UPDATE_HISTORY));
            callableStatement.setLong("I_LIST_ID", listId);
            callableStatement.setDate("I_DATE", newItemList.getSqlDate());
            callableStatement.setString("I_LOCATION", newItemList.getLocation());
            callableStatement.execute();
        } finally {
            con.close();
        }
    }

    @Override
    public List<ItemDtoList> getHisTory() throws SQLException {
        List<ItemDtoList> history = new ArrayList<>();
        Connection con = dataSource.getConnection();
        try {
            CallableStatement callableStatement = con.prepareCall((SQL_GET_HISTORY));
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                ItemDtoList itemDtoList = new ItemDtoList();
                itemDtoList.setListId(resultSet.getLong("ID"));
                itemDtoList.setCreateDate(resultSet.getDate("CREATE_DATE").toString());
                itemDtoList.setLocation(resultSet.getString("LOCATION"));
                history.add(itemDtoList);
            }
        } finally {
            con.close();
        }
        return history;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
