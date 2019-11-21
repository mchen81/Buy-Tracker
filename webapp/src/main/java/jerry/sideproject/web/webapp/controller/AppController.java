package jerry.sideproject.web.webapp.controller;

import jerry.sideproject.web.webapp.services.interfaces.ShoppingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @Autowired
    private ShoppingHistoryService shoppingHistoryService;

    @GetMapping("/index")
    public String showIndex(Model model) {
        model.addAttribute("history", shoppingHistoryService.findAll());
        model.addAttribute("listId", "");
        return "index";
    }
}
