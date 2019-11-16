package jerry.sideproject.web.webapp.controller;

import jerry.sideproject.web.webapp.controller.beans.UserDto;
import jerry.sideproject.web.webapp.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        UserDto userDto = new UserDto();
        userDto.setUserName(username);
        userDto.setPassword(password);

        //userService.logIn(userDto);


        return "";
    }


}
