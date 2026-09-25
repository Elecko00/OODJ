import javax.swing.*;
import java.awt.*;

public class medicalmanager extends JFrame {

    private String managerId;

    public medicalmanager(String managerId) {

        this.managerId = managerId;

        setTitle("Medical Manager Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton profileButton = new JButton("Edit Profile");
        JButton departmentButton = new JButton("Manage Department");
        JButton rosterButton = new JButton("Manage Doctor Roster");
        JButton reportButton = new JButton("View Reports");
        JButton logoutButton = new JButton("Logout");

        profileButton.addActionListener(e -> {
            new MedicalManagerProfile(managerId);
        });

        departmentButton.addActionListener(e -> {
            new DepartmentManagement(managerId);
        });

        rosterButton.addActionListener(e -> {

            String[] options = {
                    "Add Roster",
                    "View Roster",
                    "Update Roster",
                    "Delete Roster",
                    "Cancel"
            };

            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Doctor Roster Management",
                    "Manage Doctor Roster",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                new AddRoster();
            }
            else if (choice == 1) {
                new ViewRoster();
            }
            else if (choice == 2) {
                new UpdateRoster();
            }
            else if (choice == 3) {
                new DeleteRoster();
            }
        });

       reportButton.addActionListener(e -> {
            new HospitalReports();
        });

        logoutButton.addActionListener(e -> {
            dispose();
        });

        panel.add(profileButton);
        panel.add(departmentButton);
        panel.add(rosterButton);
        panel.add(reportButton);
        panel.add(logoutButton);

        add(panel);

        setVisible(true);
    }
}