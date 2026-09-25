
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadBaseFee {

    private String filePath = "data/base_fee.txt";
    private double baseFee;

    public String readFee() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();
            this.baseFee = Double.parseDouble(line.trim());

            br.close();
        } catch (IOException e) {
            return "File not found";
        }
        return "Base fee loaded";
    }

    public double returnFee() {
        return this.baseFee;
    }
}
