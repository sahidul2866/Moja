package cse2216.cse.univdhaka.edu.home;

public class foodPrice {
    private String id;
    private String foodName;
    private String foodPrice;

    public foodPrice() {

    }

    public foodPrice(String id, String foodName, String foodPrice) {
        this.id = id;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    public String getId() {
        return id;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }
}


