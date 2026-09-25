import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class PrescriptionFileManager {
    private String fileName;
    public PrescriptionFileManager() {
        fileName = "data/prescriptions.txt";
    }

    public boolean savePrescription(
        PrescriptionRecord prescription) {

    try {
        FileWriter fileWriter = new FileWriter(fileName, true);
        PrintWriter writer = new PrintWriter(fileWriter);

        writer.println(
            prescription.toTextLine()
        );

        writer.close();
        return true;

    } catch (IOException error) {
        System.out.println(
            "Error saving prescription: "
                    + error.getMessage()
        );
        return false;
        }
    }
}