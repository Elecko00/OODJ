public class Doctor extends User {

    private String email;
    private String phoneNumber;
    private String specialization;

    public Doctor(String userId, String username, String password, String name,
                  String email, String phoneNumber, String specialization) {
        super(userId, username, password, name);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
    }

    public String getUserID() {
        return getUserId();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getRole() { return "Doctor"; }

    public String toTextLine() {
        return getUserId() + ";" + getName() + ";" + email + ";" + phoneNumber + ";" + specialization;
    }
}
