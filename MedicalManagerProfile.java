import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MedicalManagerProfile extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JPasswordField passwordField;

    private final String FILE_NAME = "medicalManagers.txt";

    public MedicalManagerProfile(String managerId) {

        setTitle("Medical Manager Profile");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel idLabel = new JLabel("Manager ID:");
        idField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        idField.setEditable(false);

        panel.add(idLabel);
        panel.add(idField);

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(phoneLabel);
        panel.add(phoneField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);

        loadProfile(managerId);

        saveButton.addActionListener(e -> saveProfile());

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void loadProfile(String managerId) {

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length == 5 && data[0].equals(managerId)) {

                    idField.setText(data[0]);
                    nameField.setText(data[1]);
                    emailField.setText(data[2]);
                    phoneField.setText(data[3]);
                    passwordField.setText(data[4]);

                    return;
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Medical Manager ID not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (FileNotFoundException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "medicalManagers.txt was not found.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error reading profile.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void saveProfile() {

        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (id.isEmpty()
                || name.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (!email.contains("@") || !email.contains(".")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (!phone.matches("\\d+")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain digits only.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            ArrayList<String> records = new ArrayList<>();

            try (BufferedReader reader =
                         new BufferedReader(new FileReader(FILE_NAME))) {

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] data = line.split("\\|");

                    if (data.length == 5 && data[0].equals(id)) {

                        String updatedRecord =
                                id + "|" +
                                name + "|" +
                                email + "|" +
                                phone + "|" +
                                password;

                        records.add(updatedRecord);

                    } else {

                        records.add(line);
                    }
                }
            }

            try (FileWriter writer =
                         new FileWriter(FILE_NAME)) {

                for (String record : records) {

                    writer.write(record);
                    writer.write(System.lineSeparator());
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Profile updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (FileNotFoundException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "medicalManagers.txt was not found.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error saving profile.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new MedicalManagerProfile("MM001")
        );
    }
}