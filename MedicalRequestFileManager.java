import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class MedicalRequestFileManager {
    private String fileName;
    public MedicalRequestFileManager() {
        fileName = "data/medical_requests.txt";
    }

    public boolean saveMedicalRequest(
            MedicalRequestRecord medicalRequest){

        try {

            FileWriter fileWriter = new FileWriter(fileName, true);
            PrintWriter writer = new PrintWriter(fileWriter);

            writer.println(medicalRequest.toTextLine());
            writer.close();
            return true;

        } catch (IOException error) {
            System.out.println("Error saving medical request" + error.getMessage());
            return false;
        }
    }
}