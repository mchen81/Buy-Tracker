package jerry.sideproject.web.webapp.services;

import jerry.sideproject.web.webapp.bean.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InMemoryItemService implements ItemService {

    static Map<Long, Item> itemsDB = new HashMap<>();

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(itemsDB.values());
    }

    @Override
    public void saveAll(List<Item> items) {
        long nextId = getNextId();
        items.removeIf(i -> i.isEmpty());
        for (Item item : items) {
            if (item.getId() == 0) {
                item.setId(nextId++);
            }
        }
        Map<Long, Item> itemsMap = items.stream().collect(Collectors.toMap(Item::getId, Function.identity()));
        itemsDB.putAll(itemsMap);
    }

    @Override
    public Double getTotalAmount() {
        if (itemsDB.isEmpty()) {
            return Double.NaN;
        }
        Double total = Double.NaN;
        for (Item item : findAll()) {
            total += item.getPrice();
        }
        return total;
    }

    private Long getNextId() {
        return itemsDB.keySet()
                .stream()
                .mapToLong(value -> value)
                .max()
                .orElse(0) + 1;
    }
}
