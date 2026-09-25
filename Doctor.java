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

    // 保留组员原本用的 getUserID()（大写ID）这个名字，
    // 这样 DoctorDashboard / ConsultationFrame 等4个GUI文件完全不用改
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

    // toTextLine() 保持跟组员原本doctors.txt一样的5栏格式（分号分隔）
    public String toTextLine() {
        return getUserId() + ";" + getName() + ";" + email + ";" + phoneNumber + ";" + specialization;
    }
}
