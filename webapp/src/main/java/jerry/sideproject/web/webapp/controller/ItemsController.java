package jerry.sideproject.web.webapp.controller;

import jerry.sideproject.web.webapp.controller.beans.ItemDto;
import jerry.sideproject.web.webapp.controller.beans.ItemDtoList;
import jerry.sideproject.web.webapp.dao.interfaces.ItemDao;
import jerry.sideproject.web.webapp.dao.interfaces.ShoppingHistoryDao;
import jerry.sideproject.web.webapp.services.interfaces.ItemService;
import jerry.sideproject.web.webapp.services.interfaces.ShoppingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ShoppingHistoryService shoppingHistoryService;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ShoppingHistoryDao shoppingHistoryDao;

    @PostConstruct
    public void loadHistory() throws SQLException {
        shoppingHistoryService.loadHistoryFromDB();
    }

    @ModelAttribute("categoryList")
    public List<String> categoryMap() throws SQLException {
        Map<Long, String> categoriesMap = itemDao.getCategoriesMap();
        List<Long> sortIds = new ArrayList<>(categoriesMap.keySet());
        Collections.sort(sortIds);
        List<String> sortedCategoryMap = new ArrayList<>();
        for (Long id : sortIds) {
            sortedCategoryMap.add(categoriesMap.get(id));
        }
        return sortedCategoryMap;
    }

    @GetMapping(value = "/all/{listId}")
    public String showNewForm(Model model, @PathVariable("listId") Long listId) {
        if (listId.equals(0L)) {
            itemService.clear();
        } else {
            ItemDtoList itemDtoList = shoppingHistoryService.getItemDtoListById(listId);
            model.addAttribute("buyingDate", itemDtoList.getCreateDate());
            model.addAttribute("location", itemDtoList.getLocation());
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
    public String saveItems(@ModelAttribute ItemDtoList form,
                            @RequestParam String listId, Model model){
        itemService.saveAll(form.getItems());
        Long listIdNumber = Long.valueOf(listId);
        if (listIdNumber.equals(0L)) {
            form.setCreateDate(getCurrentDate());
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

    @PostMapping(value = "/submitToDB")
    public String submit(Model model,
                         @RequestParam("buyingDate") String buyingDate,
                         @RequestParam String location,
                         @RequestParam Long listId) throws SQLException {
        ItemDtoList itemDtoList = shoppingHistoryService.getItemDtoListById(listId);
        itemDtoList.setCreateDate(buyingDate);
        itemDtoList.setLocation(location);
        shoppingHistoryService.editItemDtoList(listId, itemDtoList);
        for (ItemDtoList items : shoppingHistoryService.findAll()) {
            if (!items.isUpdated()) {
                shoppingHistoryDao.updateHistory(itemDtoList, itemDtoList.getListId());
            }
        }
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

    private String getCurrentDate() {
        return Date.valueOf(LocalDate.now()).toString();
    }
}
