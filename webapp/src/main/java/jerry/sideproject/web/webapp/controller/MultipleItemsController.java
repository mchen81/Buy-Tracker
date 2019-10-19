package jerry.sideproject.web.webapp.controller;

import jerry.sideproject.web.webapp.bean.Item;
import jerry.sideproject.web.webapp.bean.ItemsCreationDto;
import jerry.sideproject.web.webapp.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/items")
public class MultipleItemsController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("total", itemService.getTotalAmount());
        return "allItems";
    }

    @GetMapping(value = "/create")
    public String showCreateForm(Model model) {
        ItemsCreationDto itemForm = new ItemsCreationDto();
        for (int i = 1; i <= 3; i++) {
            itemForm.addItem(new Item());
        }
        model.addAttribute("form", itemForm);
        return "createItemsForm";
    }

    @GetMapping(value = "/edit")
    public String showEditForm(Model model) {
        List<Item> items = new ArrayList<>();
        itemService.findAll()
                .iterator()
                .forEachRemaining(items::add);
        model.addAttribute("form", new ItemsCreationDto(items));
        return "editItemssForm";
    }

    @PostMapping(value = "/save")
    public String saveItems(@ModelAttribute ItemsCreationDto form, Model model) {
        itemService.saveAll(form.getItems());
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("total", itemService.getTotalAmount());
        return "redirect:/items/all";
    }
}
