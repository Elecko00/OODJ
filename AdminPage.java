import javax.swing.*;
import java.awt.*;

public class AdminPage extends JFrame {

    private Admin admin;

    public AdminPage(Admin admin) {

        this.admin = admin;

        setTitle("HMS - Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(330, 330);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel welcomeLabel =
                new JLabel("Welcome, " + admin.returnName());

        welcomeLabel.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        welcomeLabel.setBounds(17, 20, 280, 25);

        JButton adminButton =
                new JButton("Manage Admin");

        JButton roomButton =
                new JButton("Manage Rooms");

        JButton insuranceButton =
                new JButton("Manage Insurance");

        JButton baseFeeButton =
                new JButton("Manage Base Fee");

        JButton exitButton =
                new JButton("Exit");

        adminButton.setBounds(17, 60, 280, 30);
        roomButton.setBounds(17, 100, 280, 30);
        insuranceButton.setBounds(17, 140, 280, 30);
        baseFeeButton.setBounds(17, 180, 280, 30);
        exitButton.setBounds(17, 230, 280, 30);

        panel.add(welcomeLabel);
        panel.add(adminButton);
        panel.add(roomButton);
        panel.add(insuranceButton);
        panel.add(baseFeeButton);
        panel.add(exitButton);

        add(panel);

        adminButton.addActionListener(e -> {

            AdminManagementFrame frame =
                    new AdminManagementFrame();

            frame.setVisible(true);
        });

        roomButton.addActionListener(e -> {

            RoomManagementFrame frame =
                    new RoomManagementFrame();

            frame.setVisible(true);
        });

        insuranceButton.addActionListener(e -> {

            InsuranceManagementFrame frame =
                    new InsuranceManagementFrame();

            frame.setVisible(true);
        });

        baseFeeButton.addActionListener(e -> {

            BaseFeeFrame frame =
                    new BaseFeeFrame();

            frame.setVisible(true);
        });

        exitButton.addActionListener(e -> dispose());
    }
}