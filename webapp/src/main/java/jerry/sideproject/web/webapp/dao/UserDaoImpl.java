package jerry.sideproject.web.webapp.dao;

import jerry.sideproject.web.webapp.dao.beans.UserDo;
import jerry.sideproject.web.webapp.dao.interfaces.UserDao;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDaoImpl implements UserDao {

    private DataSource dataSource;

    private static final String SQL_REG_USER = "{CALL REG_USER(?,?)}";

    private static final String SQL_GET_USER = "{CALL GET_USER(?)}";

    @Override
    public int registerUser(UserDo user) throws SQLException {
        Connection con = dataSource.getConnection();
        CallableStatement callableStatement = con.prepareCall((SQL_REG_USER));
        callableStatement.setString("i_USER_NAME", user.getUserName());
        callableStatement.setString("i_USER_KEY", user.getKey());
        try {
            ResultSet resultSet = callableStatement.executeQuery();
            return resultSet.getInt("REG_RESULT");
        } finally {
            con.close();
        }
    }

    @Override
    public UserDo getUser(String userName) throws SQLException {
        Connection con = dataSource.getConnection();
        CallableStatement callableStatement = con.prepareCall((SQL_GET_USER));
        callableStatement.setString("i_USER_NAME", userName);
        UserDo userDo = null;
        try {
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                userDo = new UserDo();
                userDo.setUserId(resultSet.getLong("ID"));
                userDo.setKey(resultSet.getString("KEY"));
                userDo.setUserName(resultSet.getString("NAME"));
            }
            return userDo;
        } finally {
            con.close();
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
