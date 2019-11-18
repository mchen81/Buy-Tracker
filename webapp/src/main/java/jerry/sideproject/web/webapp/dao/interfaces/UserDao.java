package jerry.sideproject.web.webapp.dao.interfaces;

import jerry.sideproject.web.webapp.controller.beans.ItemDto;
import jerry.sideproject.web.webapp.dao.beans.UserDo;

import java.sql.SQLException;

public interface UserDao {

    int registerUser(UserDo user) throws SQLException;

    UserDo getUser(String userName) throws SQLException;

}
