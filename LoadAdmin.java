
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadAdmin {
    private List<Admin> adminList = new ArrayList<>();
    String filePath = "data/admin.txt";
    String line;
    public String readAdmin() {
        this.adminList.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null ) {
                String[] data = line.split(", ");
                
                int id = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                String email = data[2].trim();
                String password= data[3].trim();

                Admin a = new Admin(id, email, password, name, email);

                this.adminList.add(a);
            }
            br.close();
        } catch (IOException e) {
            return "Error reading admin data";
        } catch (NumberFormatException e) {
            return "Error parsing admin data";
        }
        return "Admin data loaded successfully";
    }

    public List<Admin> returnAdmin() {
        return new ArrayList<>(this.adminList);
    }
}