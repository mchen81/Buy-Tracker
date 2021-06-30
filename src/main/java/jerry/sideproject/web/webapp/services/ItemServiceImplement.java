package jerry.sideproject.web.webapp.services;

import jerry.sideproject.web.webapp.controller.beans.ItemDto;
import jerry.sideproject.web.webapp.dao.interfaces.ItemDao;
import jerry.sideproject.web.webapp.services.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemServiceImplement implements ItemService {

    /**
     * storing current items with incremented id
     */
    static Map<Long, ItemDto> itemsDB = new HashMap<>();


    @Override
    public List<ItemDto> findAll() {
        return new ArrayList<>(itemsDB.values());
    }

    @Override
    public void saveAll(List<ItemDto> itemDtos) {
        long nextId = getNextId();
        itemDtos.removeIf(i -> i.isEmpty()); // remove the item if it's empty
        for (ItemDto itemDto : itemDtos) {
            if (itemDto.getId() == 0) {
                itemDto.setId(nextId++);
            }
        }
        Map<Long, ItemDto> itemsMap = itemDtos.stream().collect(Collectors.toMap(ItemDto::getId, Function.identity()));
        itemsDB.putAll(itemsMap);
    }

    @Override
    public Double getTotalAmount() {
        if (itemsDB.isEmpty()) {
            return 0D;
        }
        Double total = 0D;
        for (ItemDto itemDto : findAll()) {
            total += itemDto.getPrice();
        }
        return total;
    }

    @Override
    public void clear() {
        itemsDB = new HashMap<>();
    }

    @Override
    public void remove(Long id) {
        if (itemsDB.containsKey(id)) {
            itemsDB.remove(id);
        }
    }

    @Override
    public void addItem(ItemDto itemDto) {
        long nextId = getNextId();
        itemsDB.put(nextId, itemDto);

    }

    /**
     * @return current max id + 1
     */
    private Long getNextId() {
        return itemsDB.keySet()
                .stream()
                .mapToLong(value -> value)
                .max()
                .orElse(0) + 1;
    }
}
