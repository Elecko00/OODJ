import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MedicalRequestFrame extends JFrame
        implements ActionListener {

    private Doctor doctor;
    private JTextField requestIDField;
    private JTextField patientIDField;
    private JTextField doctorIDField;
    private JTextField dateField;
    private JTextField requestTypeField;
    private JTextField statusField;
    private JTextArea requestDetailsArea;
    private JButton sendButton;
    private JButton backButton;

    public MedicalRequestFrame(Doctor doctor) {
        this.doctor = doctor;
        setTitle("Send Medical Request");
        setSize(600,450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8,2,10,10));

        requestIDField = new JTextField();
        patientIDField = new JTextField();
        doctorIDField = new JTextField(this.doctor.getUserID());

        dateField = new JTextField();
        requestTypeField = new JTextField();
        requestDetailsArea = new JTextArea();
        statusField = new JTextField("Pending");

        doctorIDField.setEditable(false);
        statusField.setEditable(false);

        sendButton = new JButton("Send Request");
        backButton = new JButton("Back");

        add(new JLabel("Request ID:"));
        add(requestIDField);

        add(new JLabel("Patient ID:"));
        add(patientIDField);

        add(new JLabel("Doctor ID:"));
        add(doctorIDField);

        add(new JLabel("Date (YYYY-MM-DD):"));
        add(dateField);

        add(new JLabel("Type (Lab Test / X-ray / Specialized Imaging):"));
        add(requestTypeField);

        add(new JLabel("Request Details:"));
        add(requestDetailsArea);

        add(new JLabel("Status:"));
        add(statusField);

        add(sendButton);
        add(backButton);

        sendButton.addActionListener(this);
        backButton.addActionListener(this);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override 
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == sendButton) {
            sendMedicalRequest();
        } else if (event.getSource() == backButton) {
            dispose();
        }
    }

    private void sendMedicalRequest() {
        String requestID =
            requestIDField.getText().trim();

        String patientID =
            patientIDField.getText().trim();

        String doctorID =
            this.doctor.getUserID();

        String requestDate =
            dateField.getText().trim();

        String requestType =
            requestTypeField.getText().trim();

        String requestDetails =
            requestDetailsArea.getText().trim();

        String requestStatus =
            statusField.getText().trim();

        if (requestID.isEmpty()
               || patientID.isEmpty()
               || doctorID.isEmpty()
               || requestDate.isEmpty()
               || requestType.isEmpty()
               || requestDetails.isEmpty()
               || requestStatus.isEmpty()) {

            JOptionPane.showMessageDialog(this,"Please complete all medical request fields.","Input error",JOptionPane.ERROR_MESSAGE);

            return;
        }
        if (requestID.contains(";")
            || patientID.contains(";")
            || requestDate.contains(";")
            || requestType.contains(";")
            || requestDetails.contains(";")
            || requestStatus.contains(";")) {

           JOptionPane.showMessageDialog(
            this,"The semicolon character is not allowed.","Input Error",JOptionPane.ERROR_MESSAGE);

            return;
        }
        if (!requestType.equalsIgnoreCase("Lab Test")
            && !requestType.equalsIgnoreCase("X-ray")
            && !requestType.equalsIgnoreCase(
                    "Specialized Imaging")) {
            JOptionPane.showMessageDialog(
                this,"Type must be Lab Test, X-ray, or Specialized Imaging.","Input Error",JOptionPane.ERROR_MESSAGE);

            return;
        }
        if (!isValidDate(requestDate)) {
            JOptionPane.showMessageDialog(this,"Enter the date using YYYY-MM-DD.","Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (requestType.equalsIgnoreCase("Lab Test")) {
           requestType = "Lab Test";
        } else if (requestType.equalsIgnoreCase("X-ray")) {
            requestType = "X-ray";
        } else {
            requestType = "Specialized Imaging";
        }

        requestDetails = requestDetails.replace("\r"," ");
        requestDetails = requestDetails.replace("\n"," ");

        MedicalRequestRecord medicalRequest = new MedicalRequestRecord(requestID, patientID, doctorID, requestDate, requestType, requestDetails, requestStatus);
        MedicalRequestFileManager fileManager = new MedicalRequestFileManager();

        boolean saveSuccessful = fileManager.saveMedicalRequest(medicalRequest);

        if (saveSuccessful == true) {
            JOptionPane.showMessageDialog(this,"Medical Request send successfully","Request send",JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,"Medical Request could not be saved","File Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidDate(String date) {
        if(date.length() != 10) {
            return false;
        }
        if (date.charAt(4) != '-'
                || date.charAt(7) != '-') {
            return false;
        }

        for (int i = 0; i < date.length(); i++) {
            if (i != 4 && i != 7) {
            char currentCharacter =
                    date.charAt(i);
             if (currentCharacter < '0'
                    || currentCharacter > '9') {

                return false;
            }
        }
    }

    int month = Integer.parseInt(
            date.substring(5, 7));

    int day = Integer.parseInt(
            date.substring(8, 10));

    if (month < 1 || month > 12) {
        return false;}

    if (day < 1 || day > 31) {
        return false;}
    return true;
}
}




