package cse2216.cse.univdhaka.edu.home;

public class RestaurentList {
    String id;
    String name;
    String address;
    String cuisines;
    String minOrder;
    String admin;

    public RestaurentList() {
    }

    public RestaurentList(String id, String name, String address, String cuisines, String minOrder,String admin) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisines = cuisines;
        this.minOrder = minOrder;
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public String getAdmin() {
        return admin;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCuisines() {
        return cuisines;
    }

    public String getMinOrder() {
        return minOrder;
    }
}
