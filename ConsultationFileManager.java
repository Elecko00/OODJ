import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class ConsultationFileManager {
    private String fileName;
    public ConsultationFileManager() {
        fileName = "data/consultations.txt";
    }

    public boolean consultationIDExists(
        String consultationID) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] consultationData = line.split(";");

                if (consultationData.length > 0 
                    && consultationData[0].equalsIgnoreCase(consultationID)) {
                        reader.close();
                        return true;
                }
            }
            reader.close();
        } catch (IOException error) {
            System.out.println("Error checking consultation ID: " + error.getMessage());
        }
        return false;
    }
    

    public boolean saveConsultation(
        ConsultationRecord consultation) {

       try {
        FileWriter fileWriter = new FileWriter(fileName, true);
        PrintWriter writer = new PrintWriter(fileWriter);

        writer.println(consultation.toTextLine()
       );

       writer.close();
       return true;

       } catch (IOException error) {
           System.out.println(
            "Error saving consultation: " + error.getMessage()
           );

           return false;
       }
    }
}