package jerry.sideproject.web.webapp.controller;

import jerry.sideproject.web.webapp.controller.beans.ItemDto;
import jerry.sideproject.web.webapp.controller.beans.ItemDtoList;
import jerry.sideproject.web.webapp.services.interfaces.ItemService;
import jerry.sideproject.web.webapp.services.interfaces.ShoppingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ShoppingHistoryService shoppingHistoryService;

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("total", String.format("%.2f", itemService.getTotalAmount()));
        model.addAttribute("history", shoppingHistoryService.findAll());

        return "allItems";
    }

    @GetMapping(value = "/recall")
    public String showOneHistory(Model model, @RequestParam Long listId) {
        itemService.clear();
        ItemDtoList itemDtoList = shoppingHistoryService.getItemDtoListById(listId);
        model.addAttribute("listID", listId.toString());
        itemService.saveAll(itemDtoList.getItems());
        return showAll(model);
    }


    @GetMapping(value = "/create")
    public String showCreateForm(Model model, @ModelAttribute String listId) {
        ItemDtoList itemForm = new ItemDtoList();
        for (int i = 1; i <= 3; i++) {
            itemForm.addItem(new ItemDto());
        }
        itemForm.setListId(listId == null || listId.isEmpty() ? null : Long.valueOf(listId));
        model.addAttribute("form", itemForm);
        return "createItemsForm";
    }

    @GetMapping(value = "/edit")
    public String showEditForm(Model model, @ModelAttribute String listId) {
        List<ItemDto> itemDtos = new ArrayList<>();
        itemService.findAll()
                .iterator()
                .forEachRemaining(itemDtos::add);
        ItemDtoList itemDtoList = new ItemDtoList(itemDtos);
        itemDtoList.setListId(listId == null || listId.isEmpty() ? null : Long.valueOf(listId));
        model.addAttribute("form", itemDtoList);
        return "editItemsForm";
    }

    @PostMapping(value = "/save")
    public String saveItems(@ModelAttribute ItemDtoList form, Model model) {
        itemService.saveAll(form.getItems());
        if (form.getListId() == null) {
            form.setCreateDate(LocalDateTime.now().toLocalDate().toString());
            form.setLocation("San Francisco");
            shoppingHistoryService.addToHistory(form);
        } else {
            shoppingHistoryService.editItemDtoList(form.getListId(), form);
        }
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

        form.setItems(itemService.findAll());
        shoppingHistoryService.editItemDtoList(form.getListId(), form);
        return "redirect:/items/all";
    }

    @GetMapping(value = "/submitToDB")
    public String submit(Model model) {
        model.addAttribute("history", shoppingHistoryService.findAll());
        return "redirect:/index";

    }

    @GetMapping(value = "/clear")
    public String clear(Model model) {
        itemService.clear();
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("total", String.format("%.2f", itemService.getTotalAmount()));
        return "redirect:/items/all";
    }
}
