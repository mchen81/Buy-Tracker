package jerry.sideproject.web.webapp.dao;

import jerry.sideproject.web.webapp.bean.Item;
import jerry.sideproject.web.webapp.bean.UserInfo;
import jerry.sideproject.web.webapp.dao.interfaces.UserDao;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private DataSource dataSource;

    private static final String SQL_REG_USER = "{CALL REG_USER(?,?,?)}";

    private static final String SQL_GET_USER = "{CALL GET_USER(?)}";

    @Override
    public int registerUser(UserInfo user) throws SQLException {
        Connection con = null;
        con = dataSource.getConnection();
        CallableStatement callableStatement = con.prepareCall((SQL_REG_USER));
        callableStatement.setLong("i_USER_ID", user.getUserId());
        callableStatement.setString("i_USER_NAME", user.getUserName());
        callableStatement.setString("i_USER_KEY", user.getKey());
        ResultSet resultSet = callableStatement.executeQuery();
        con.close();

        int result = 0;
        if (resultSet.next()) {
            result = resultSet.getInt("REG_RESULT");
        }
        return result;
    }

    @Override
    public int userValid(UserInfo user) throws SQLException {
        Connection con = null;
        con = dataSource.getConnection();
        CallableStatement callableStatement = con.prepareCall((SQL_GET_USER));
        callableStatement.setString("i_USER_NAME", user.getUserName());
        ResultSet resultSet = callableStatement.executeQuery();

        UserInfo userInDB = null;
        if (resultSet.next()) {
            userInDB = new UserInfo();
            userInDB.setKey(resultSet.getString("USER_KEY"));
        }

        if (userInDB == null) {
            return 0; // user not registered
        } else {
            if (!user.getKey().equals(userInDB.getKey())) {
                return -1; // password wrong
            }
        }
        return 1; // valid
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
