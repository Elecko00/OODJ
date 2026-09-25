public class Manager extends User {

    private String email;
    private String phoneNumber;

    public Manager(String userId, String username, String password, String name,
                   String email, String phoneNumber) {
        super(userId, username, password, name);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    public String getRole() { return "Manager"; }
}
