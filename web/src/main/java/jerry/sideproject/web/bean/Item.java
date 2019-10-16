package jerry.sideproject.web.bean;

/**
 * Buying Item
 */
public class Item {

    private String name;
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return true where name or price is empty or 0
     */
    public boolean isEmpty() {
        return null == name || null == price || name.trim().length() == 0 || price == 0.0F;
    }

    @Override
    public String toString() {
        return String.format("%s: $%.2f", name, price);
    }
}
