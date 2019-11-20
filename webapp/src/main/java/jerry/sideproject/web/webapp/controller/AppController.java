package jerry.sideproject.web.webapp.controller;

import jerry.sideproject.web.webapp.services.interfaces.ShoppingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    private ShoppingHistoryService shoppingHistoryService;

    @GetMapping("/")
    public String showIndex(Model model) {
        shoppingHistoryService.initialize();
        model.addAttribute("history", shoppingHistoryService.findAll());
        return "index";
    }
}
