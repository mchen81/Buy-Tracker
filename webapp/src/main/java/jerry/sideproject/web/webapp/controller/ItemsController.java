package jerry.sideproject.web.webapp.controller;

import jerry.sideproject.web.webapp.controller.beans.ItemDto;
import jerry.sideproject.web.webapp.controller.beans.ItemDtoList;
import jerry.sideproject.web.webapp.services.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("total", String.format("%.2f", itemService.getTotalAmount()));
        return "allItems";
    }

    @GetMapping(value = "/create")
    public String showCreateForm(Model model) {
        ItemDtoList itemForm = new ItemDtoList();
        for (int i = 1; i <= 3; i++) {
            itemForm.addItem(new ItemDto());
        }
        model.addAttribute("form", itemForm);
        return "createItemsForm";
    }

    @GetMapping(value = "/edit")
    public String showEditForm(Model model) {
        List<ItemDto> itemDtos = new ArrayList<>();
        itemService.findAll()
                .iterator()
                .forEachRemaining(itemDtos::add);
        model.addAttribute("form", new ItemDtoList(itemDtos));
        return "editItemsForm";
    }

    @PostMapping(value = "/save")
    public String saveItems(@ModelAttribute ItemDtoList form, Model model) {
        itemService.saveAll(form.getItems());
        model.addAttribute("items", itemService.findAll());
        return "redirect:/items/all";
    }

    @PostMapping(value = "/saveEdition")
    public String saveEdition(@ModelAttribute ItemDtoList form, @RequestParam("deleteItems") @Nullable Long[] deleteItems, Model model) {
        itemService.saveAll(form.getItems());
        model.addAttribute("items", itemService.findAll());
        if (deleteItems != null) {
            for (Long id : deleteItems) {
                itemService.remove(id);
            }
        }
        return "redirect:/items/all";
    }

    @GetMapping(value = "/submitToDB")
    public String submit() {
        return "TODO"; // TODO
    }

    @GetMapping(value = "/clear")
    public String clear(Model model) {
        itemService.clear();
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("total", String.format("%.2f", itemService.getTotalAmount()));
        return "redirect:/items/all";
    }
}
