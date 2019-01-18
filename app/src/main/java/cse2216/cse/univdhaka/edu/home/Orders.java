package cse2216.cse.univdhaka.edu.home;

public class Orders {
    String id;
    String user;
    String name;
    Double price;
    int quantity;
    Double total;
    String reName;

    public Orders() {
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public Orders(String id, String user, String name, Double price, int quantity, Double total, String reName) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.price = price;

        this.quantity = quantity;
        this.total = total;

        this.reName = reName;
    }

    public String getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getTotal() {
        return total;
    }

    public String getReName() {
        return reName;
    }
}


