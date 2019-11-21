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

    @GetMapping(value = "/all/{listId}")
    public String showNewForm(Model model, @PathVariable("listId") Long listId) {
        if (listId.equals(0L)) {
            itemService.clear();
        }
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("total", String.format("%.2f", itemService.getTotalAmount()));
        model.addAttribute("listId", listId);
        return "allItems";
    }

    @GetMapping(value = "/recall")
    public String showOneHistory(Model model, @RequestParam Long listId) {
        itemService.clear();
        ItemDtoList itemDtoList = shoppingHistoryService.getItemDtoListById(listId);
        model.addAttribute("form", itemDtoList);
        itemService.saveAll(itemDtoList.getItems());
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("total", String.format("%.2f", itemService.getTotalAmount()));
        model.addAttribute("history", shoppingHistoryService.findAll());
        return "redirect:/items/all/" + listId.toString();
    }

    @GetMapping(value = "/create/{listId}")
    public String showCreateForm(Model model, @PathVariable("listId") String listId) {
        System.out.println("In Create Form, list Id is " + listId);
        ItemDtoList itemForm = new ItemDtoList();
        for (int i = 1; i <= 3; i++) {
            itemForm.addItem(new ItemDto());
        }
        itemForm.setListId(listId.equals("0") ? null : Long.valueOf(listId));
        model.addAttribute("form", itemForm);
        return "createItemsForm";
    }

    @GetMapping(value = "/edit/{listId}")
    public String showEditForm(Model model, @PathVariable("listId") String listId) {
        System.out.println("In Edit Form, list Id is " + listId);
        List<ItemDto> itemDtos = new ArrayList<>();
        itemService.findAll()
                .iterator()
                .forEachRemaining(itemDtos::add);
        ItemDtoList itemDtoList = new ItemDtoList(itemDtos);
        itemDtoList.setListId(Long.valueOf(listId));
        model.addAttribute("form", itemDtoList);
        return "editItemsForm";
    }

    @PostMapping(value = "/save")
    public String saveItems(@ModelAttribute ItemDtoList form, @RequestParam String listId, Model model) {
        System.out.println("In saving form, list id is " + listId);
        itemService.saveAll(form.getItems());
        System.out.println("Before saving process, listId is " + listId);
        Long listIdNumber = Long.valueOf(listId);
        if (listIdNumber.equals(0L)) {
            form.setCreateDate(LocalDateTime.now().toLocalDate().toString());
            form.setLocation("San Francisco");
            listIdNumber = shoppingHistoryService.addToHistory(form);
        } else {
            ItemDtoList editedForm = new ItemDtoList();
            editedForm.setItems(itemService.findAll());
            editedForm.setListId(listIdNumber);
            shoppingHistoryService.editItemDtoList(listIdNumber, editedForm);
        }
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("listId", listId);
        System.out.println("After saving process, listId is " + form.getListId());
        return "redirect:/items/all/" + listIdNumber.toString();
    }

    @PostMapping(value = "/saveEdition")
    public String saveEdition(@ModelAttribute ItemDtoList form,
                              @RequestParam String listId,
                              @RequestParam("deleteItems") @Nullable Long[] deleteItems,
                              Model model) {
        itemService.saveAll(form.getItems());
        model.addAttribute("items", itemService.findAll());
        if (deleteItems != null) {
            for (Long id : deleteItems) {
                itemService.remove(id);
            }
        }
        form.setItems(itemService.findAll());
        shoppingHistoryService.editItemDtoList(Long.valueOf(listId), form);
        model.addAttribute("listId", listId);
        return "redirect:/items/all/" + listId;
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
        return "redirect:/items/all/0";
    }
}
