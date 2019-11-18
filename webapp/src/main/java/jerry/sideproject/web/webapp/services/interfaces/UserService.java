package jerry.sideproject.web.webapp.services.interfaces;

import jerry.sideproject.web.webapp.controller.beans.UserDto;
import jerry.sideproject.web.webapp.services.exceptions.UserDoesNotExistException;
import jerry.sideproject.web.webapp.services.exceptions.UserHasExistException;
import jerry.sideproject.web.webapp.services.exceptions.WrongPasswordException;

public interface UserService {

    /**
     * log in user
     *
     * @param userDto
     * @return User's Id in DB
     * @throws UserDoesNotExistException
     * @throws WrongPasswordException
     */
    long logIn(UserDto userDto) throws UserDoesNotExistException, WrongPasswordException;

    /**
     * register an User
     *
     * @param userDto
     * @return User's Id in DB
     * @throws UserHasExistException
     */
    long register(UserDto userDto) throws UserHasExistException;
}
