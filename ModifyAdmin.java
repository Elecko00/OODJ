
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ModifyAdmin {
    private String filePath = "data/admin.txt";
    private int selectedId;
    private String name;
    private String email;
    private String password;

    public String writeAdmin(int selectedId, String name, String email, String password, String action) {

        LoadAdmin loadAdmin = new LoadAdmin();
        loadAdmin.readAdmin();
        List<Admin> admins = loadAdmin.returnAdmin();
        try {
            if (action.equals("update")) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
                for (Admin a : admins) {
                    if (a.returnID() == selectedId) {
                        writer.write(selectedId + ", " + name + ", " + email + ", " + password);
                        writer.newLine();
                    } else {
                        writer.write(a.returnID() + ", " + a.returnName() + ", " + a.returnEmail() + ", " + a.returnPassword());
                        writer.newLine();
                    }
                }
                writer.close();
                return "Update completed";
            } else if (action.equals("append")) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
                writer.write(selectedId + ", " + name + ", " + email + ", " + password);
                writer.newLine();
                writer.close();
                return "Add user completed";
            } else if (action.equals("delete")) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
                for (Admin a : admins) {
                    if (a.returnID() != selectedId) {
                        writer.write(a.returnID() + ", " + a.returnName() + ", " + a.returnEmail() + ", " + a.returnPassword());
                        writer.newLine();
                    }
                }
                writer.close();
                return "Delete user completed";
            } else {
                return "Invalid action selected.";
            }
            
        } catch (IOException e) {
            return "File not found";
        }
    }

    public int getLatestId() {
        LoadAdmin loadAdmin = new LoadAdmin();
        loadAdmin.readAdmin();
        List<Admin> admins = loadAdmin.returnAdmin();
        int latestId = 0;
        for (Admin a : admins) {
            if (a.returnID() > latestId) {
                latestId = a.returnID();
            }
        }
        return latestId;
    }
}
