package jerry.sideproject.web.webapp.dao.beans;

public class ItemDo {

    private long id;
    private long buyingListId;
    private long categoryId;
    private String name;
    private Double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBuyingListId() {
        return buyingListId;
    }

    public void setBuyingListId(long buyingListId) {
        this.buyingListId = buyingListId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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

    public void setPrice(Double price) {
        this.price = price;
    }
}
