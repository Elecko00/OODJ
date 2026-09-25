public class User {
    private String userId;
    private String username;
    private String password;
    private String name;

    public User(String userId, String username, String password, String name) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
