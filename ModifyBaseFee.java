
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ModifyBaseFee {

    private String filePath = "data/base_fee.txt";
    
    public String writeFee(double newFee) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false));
            String newFee1 = String.valueOf(newFee);
            bw.write(newFee1);
            bw.close();
        } catch (IOException e) {
            return "File not found";
        }
        return "Base fee updated";
    }

}
