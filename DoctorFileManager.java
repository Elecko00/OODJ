import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class DoctorFileManager {
    private String fileName;
    
    public DoctorFileManager() {
        fileName = "data/doctors.txt";
    }

    // 新增了 username / password 两个参数：这两个是登入时从统一的 users.txt 读到的，
    // doctors.txt 本身没有帐密，所以要由呼叫端（Main）把登入验证时拿到的帐密传进来，
    // 拼成一个完整的 Doctor 物件（Doctor 现在 extends User，缺username/password会编译失败）
    public Doctor loadDoctorByID(String doctorID, String username, String password) {

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
