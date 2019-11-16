package jerry.sideproject.web.webapp.dao.interfaces;

import jerry.sideproject.web.webapp.bean.UserInfo;

import java.sql.SQLException;

public interface UserDao {

    int registerUser(UserInfo user) throws SQLException;

    int userValid(UserInfo user) throws SQLException;

}
