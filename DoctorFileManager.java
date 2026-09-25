import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DoctorFileManager {
    private String fileName;
    
    public DoctorFileManager() {
        fileName = "data/doctors.txt";
    }

    public Doctor loadDoctorByID(String doctorID, String username, String password) {
        File doctorFile = new File(fileName);
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        System.out.println("Doctor file path: " + doctorFile.getAbsolutePath());
        System.out.println("Doctor file exists: " + doctorFile.exists());

        try {
            BufferedReader reader = new BufferedReader( new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] doctorData = line.split(";");

                if (doctorData.length == 5 && doctorData[0].equals(doctorID)) {

                    Doctor doctor = new Doctor(
                            doctorData[0],   // userId (doctorID)
                            username,
                            password,
                            doctorData[1],   // name
                            doctorData[2],   // email
                            doctorData[3],   // phoneNumber
                            doctorData[4]    // specialization
                    );
                    reader.close();
                    return doctor;
                }
            }
            reader.close();
        } catch (IOException error) {
            System.out.println("Error reading file: " + error.getMessage());
        }
        return null;
    }
    
    public boolean updateDoctor(Doctor updatedDoctor) {

    String updatedFileContent = "";
    boolean doctorFound = false;

    try {

        BufferedReader reader =
                new BufferedReader(
                        new FileReader(fileName)
                );

        String line;

        while ((line = reader.readLine()) != null) {

            String[] doctorData = line.split(";");

            if (doctorData.length == 5
                    && doctorData[0].equals(
                            updatedDoctor.getUserID())) {

                updatedFileContent =
                        updatedFileContent
                        + updatedDoctor.toTextLine()
                        + "\n";

                doctorFound = true;

            } else {

                updatedFileContent =
                        updatedFileContent
                        + line
                        + "\n";
            }
        }

        reader.close();

        if (doctorFound == false) {

            return false;
        }

        PrintWriter writer =
                new PrintWriter(
                        new FileWriter(fileName)
                );

        writer.print(updatedFileContent);
        writer.close();

        return true;

    } catch (IOException error) {

        System.out.println(
                "Error updating doctor file: "
                        + error.getMessage()
        );

        return false;
    }
}
}
