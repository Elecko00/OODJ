
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ModifyInsurance  {

    private String filePath = "data/insurance_lists.txt";

    public String writeInsurance(int selectedInsuranceId, String insuranceName, boolean insuranceValidity, String action) {
        try {
            LoadInsurance li = new LoadInsurance();
            li.readInsurance();
            List<Insurance> liList = li.returnInsurance();
            if (action.equals("add")) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
                bw.write(selectedInsuranceId + ", " + insuranceName + ", " + insuranceValidity);
                bw.newLine();
                bw.close();
            } else if (action.equals("update")) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false));
                for (Insurance i : liList) {
                    if (i.returnInsuranceId() == selectedInsuranceId) {
                        bw.write(selectedInsuranceId + ", " + insuranceName + ", " + insuranceValidity);
                        bw.newLine();
                    } else {
                        bw.write(i.returnInsuranceId() + ", " + i.returnInsuranceName() + ", " + i.returnInsuranceValidity());
                        bw.newLine();
                    }
                }
                bw.close();
            } else if (action.equals("delete")) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false));
                for (Insurance i : liList) {
                    if (i.returnInsuranceId() != selectedInsuranceId) {
                        bw.write(i.returnInsuranceId() + ", " + i.returnInsuranceName() + ", " + i.returnInsuranceValidity());
                        bw.newLine();
                    }
                }
                bw.close();
            } else {
                return "Invalid action";
            }
        } catch (IOException e) {
            return "File not found";
        }
        return "Insurance updated successfuly";
    }

    public int returnLatestInsuranceId() {
        int lastInsuranceId = 0;
        LoadInsurance li = new LoadInsurance();
        li.readInsurance();
        List<Insurance> liList = li.returnInsurance();
        for (Insurance i : liList) {
            if (i.returnInsuranceId() > lastInsuranceId) {
                lastInsuranceId = i.returnInsuranceId();
            }
        }
        return lastInsuranceId;

    }
}