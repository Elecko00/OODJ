import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DoctorDashboard extends JFrame implements ActionListener {

    private Doctor doctor;

    private JLabel titleLabel;
    private JLabel welcomeLabel;

    private JButton profileButton;
    private JButton consultationButton;
    private JButton prescriptionButton;
    private JButton medicalRequestButton;
    private JButton logoutButton;

    public DoctorDashboard(Doctor doctor) {

        this.doctor = doctor;

        setTitle("Hospital Management System Doctor");
        setSize(500,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7,1,10,10));

        titleLabel = new JLabel(
                "Hospital Management System",
                SwingConstants.CENTER);

        welcomeLabel = new JLabel(
                "Welcome, " + this.doctor.getName(),
                SwingConstants.CENTER);

        profileButton = new JButton("Edit Personal Profile");

        consultationButton = new JButton("Record signs and Consultation notes");

        prescriptionButton = new JButton("Issue Medication Prescription");

        medicalRequestButton = new JButton("Request Medical Test");

        logoutButton = new JButton("Logout");

        add(titleLabel);
        add(welcomeLabel);
        add(profileButton);
        add(consultationButton);
        add(prescriptionButton);
        add(medicalRequestButton);
        add(logoutButton);

        profileButton.addActionListener(this);
        consultationButton.addActionListener(this);
        prescriptionButton.addActionListener(this);
        medicalRequestButton.addActionListener(this);
        logoutButton.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == profileButton) {

           new DoctorProfileFrame(doctor);

        } else if (event.getSource() == consultationButton) {

            new ConsultationFrame(doctor);

        } else if (event.getSource() == prescriptionButton) {

            new PrescriptionFrame(doctor);

        } else if (event.getSource() == medicalRequestButton) {
            new MedicalRequestFrame(doctor);

        } else if (event.getSource() == logoutButton) {

            int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            
            if (answer == JOptionPane.YES_OPTION) {
                dispose();
            }
        }

    }
}

