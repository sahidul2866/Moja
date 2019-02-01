package cse2216.cse.univdhaka.edu.home;

public class Orders {
    String id;
    String user;
    String name;
    Double price;
    int quantity;
    Double total;
    String reName;
    String address;
    String mobile;

    public Orders(String id, String user, String name, Double price, int quantity, Double total, String reName, String address, String mobile) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.reName = reName;
        this.address = address;
        this.mobile = mobile;
    }

    public Orders(String id, String name, Double price, int quantity, Double total) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public Orders(String id, String user, String name, int quantity, String address, String mobile) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.quantity = quantity;
        this.address = address;
        this.mobile = mobile;
    }

    public Orders() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
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

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setQuantityIncrease() {
        this.quantity = quantity + 1;
    }

    public boolean setQuantityDecrease() {
        if (this.quantity >= 1) {
            this.quantity = quantity - 1;
            return true;
        } else return false;
    }

    public void setTotalIncrease() {
        this.total = quantity * price;
    }

    public void setTotalDecrease() {
        this.total = quantity * price;
    }
}
