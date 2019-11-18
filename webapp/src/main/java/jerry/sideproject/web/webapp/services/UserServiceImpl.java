package jerry.sideproject.web.webapp.services;

import jerry.sideproject.web.webapp.controller.beans.UserDto;
import jerry.sideproject.web.webapp.dao.beans.UserDo;
import jerry.sideproject.web.webapp.dao.interfaces.UserDao;
import jerry.sideproject.web.webapp.services.exceptions.UserDoesNotExistException;
import jerry.sideproject.web.webapp.services.exceptions.UserHasExistException;
import jerry.sideproject.web.webapp.services.exceptions.WrongPasswordException;
import jerry.sideproject.web.webapp.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public long logIn(UserDto userDto) throws UserDoesNotExistException, WrongPasswordException {
        try {
            UserDo userDo = userDao.getUser(userDto.getUserName());
            if (userDo == null) {
                throw new UserDoesNotExistException();
            }
            if (!userDo.getKey().equals(hashPassword(userDto.getPassword()))) {
                throw new WrongPasswordException();
            }
            return userDo.getUserId();
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public long register(UserDto userDto) throws UserHasExistException {
        try {
            UserDo userDo = userDao.getUser(userDto.getUserName());
            if (userDo != null) {
                throw new UserHasExistException();
            }
            userDo = new UserDo();
            userDo.setUserName(userDto.getUserName());
            userDo.setKey(hashPassword(userDto.getPassword()));
            long userId = userDao.registerUser(userDo);
            return userId;
        } catch (SQLException e) {
            return -1;
        }
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Hash password To MD5
     * Code refers from https://www.geeksforgeeks.org/md5-hash-in-java/
     *
     * @param password password
     * @return key of MD5
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passwordInByte = md.digest(password.getBytes());
            BigInteger passwordInBI = new BigInteger(1, passwordInByte);
            String hashedPassword = passwordInBI.toString(16);
            while (hashedPassword.length() < 32) {
                hashedPassword = "0" + hashedPassword;
            }
            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
