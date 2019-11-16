package jerry.sideproject.web.webapp.bean;

public class Item {

    private long id;

    private String name;

    private Double price;

    private String category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(String price) {
        try {
            this.price = Double.valueOf(price);
        } catch (Exception e) {
            this.price = 0D;
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Item() {
    }

    public Item(long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (id != other.id)
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        return true;
    }

    public boolean isEmpty() {
        return name == null || price == null || name.trim().isEmpty() || price.isNaN();
    }

    @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ", price=" + price + "]";
    }
}
