
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LoadInsurance {
    
    private String filePath = "data/insurance_lists.txt";
    private List<Insurance> insuranceList = new ArrayList<>();

    public String readInsurance() {
        this.insuranceList.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                int insuranceId = Integer.parseInt(data[0].trim());
                String insuranceName = data[1].trim();
                boolean insuranceValidity = Boolean.parseBoolean(data[2].trim());
                Insurance insurance = new Insurance(insuranceId, insuranceName, insuranceValidity);
                this.insuranceList.add(insurance);
            }
        } catch (IOException e) {
            return "Error reading insurance data";
        } catch (NumberFormatException e) {
            return "Error parsing insurance data";
        }
        return "Insurance data loaded successfully";
    }

    public List<Insurance> returnInsurance() {
        return new ArrayList<>(this.insuranceList);
    }
}