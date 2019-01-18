package cse2216.cse.univdhaka.edu.home;

public class AddUserCart {

    String id;
    String foodName;
    Double Price;
    int quantity;
    Double total;

    public AddUserCart() {
    }

    public AddUserCart(String id, String foodName, Double price, int quantity,Double total) {
        this.id = id;
        this.foodName = foodName;
        Price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setQuantityIncrease()
    {
        this.quantity = quantity+1;
    }

    public void setQuantityDecrease() {
        this.quantity = quantity-1;
    }

    public Double getPrice() {

        return Price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotalIncrease() {
        this.total = total+Price;
    }

    public void setTotalDecrease() {
        this.total = total-Price;
    }
}
