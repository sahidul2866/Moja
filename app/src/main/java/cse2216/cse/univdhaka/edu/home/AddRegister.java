package cse2216.cse.univdhaka.edu.home;

public class AddRegister {
    private String id;
    private String uname;
    private String pass;
    private String Mobile;
    private String Address;
    private String resName;
    private String resAddress;
    private String type;


    public AddRegister(String id, String uname, String pass, String mobile, String address, String resName, String resAddress,String type) {
        this.id = id;
        this.uname = uname;
        this.pass = pass;
        Mobile = mobile;
        Address = address;
        this.resName = resName;
        this.resAddress = resAddress;
        this.type = type;
    }

    public AddRegister() {
    }

    public AddRegister(String id, String uname, String pass, String mobile, String address,String type) {
        this.id = id;
        this.uname = uname;
        this.pass = pass;
        Mobile = mobile;
        Address = address;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getUname() {
        return uname;
    }

    public String getPass() {
        return pass;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getAddress() {
        return Address;
    }

    public String getResAddress() {
        return resAddress;
    }

    public String getResName() {
        return resName;
    }
}
