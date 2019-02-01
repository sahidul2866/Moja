package cse2216.cse.univdhaka.edu.home;

public class AddAdminCart {

    String id;
    String adminName;
    String foodName;
    Integer quantity;
    String address;
    String phone_no;

    public AddAdminCart(String id,String adminName, String foodName, Integer quantity, String address, String phone_no) {
        this.id = id;
        this.adminName = adminName;
        this.foodName = foodName;
        this.quantity = quantity;
        this.address = address;
        this.phone_no = phone_no;
    }

    public String getId() {
        return id;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getFoodName() {
        return foodName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_no() {
        return phone_no;
    }
}
