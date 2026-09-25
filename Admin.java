public class Admin extends User {

    private int adminId;
    private String email;

    public Admin(int adminId, String username, String password, String name, String email) {
        super(String.valueOf(adminId), username, password, name);
        this.adminId = adminId;
        this.email = email;
    }

    // 保留Darren原本的方法名，让 ModifyAdmin.java 完全不用改
    public int returnID() { return adminId; }
    public String returnName() { return getName(); }
    public String returnEmail() { return email; }
    public String returnPassword() { return getPassword(); }
}
