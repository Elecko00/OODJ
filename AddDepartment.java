import javax.swing.*;
import java.awt.*;
import java.io.*;

public class AddDepartment extends JFrame {

    private JTextField departmentIdField;
    private JTextField departmentNameField;
    private JTextArea descriptionArea;

    private final String FILE_NAME = "departments.txt";

    public AddDepartment() {

        setTitle("Add Clinical Department");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel idLabel = new JLabel("Department ID:");
        departmentIdField = new JTextField();

        JLabel nameLabel = new JLabel("Department Name:");
        departmentNameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        formPanel.add(idLabel);
        formPanel.add(departmentIdField);

        formPanel.add(nameLabel);
        formPanel.add(departmentNameField);

        formPanel.add(descriptionLabel);
        formPanel.add(new JScrollPane(descriptionArea));

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        saveButton.addActionListener(e -> saveDepartment());

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void saveDepartment() {

        String departmentId =
                departmentIdField.getText().trim();

        String departmentName =
                departmentNameField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        // Check empty fields
        if (departmentId.isEmpty()
                || departmentName.isEmpty()
                || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            // Check whether Department ID already exists
            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(FILE_NAME));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length >= 1
                        && data[0].equalsIgnoreCase(departmentId)) {

                    reader.close();

                    JOptionPane.showMessageDialog(
                            this,
                            "Department ID already exists.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }
            }

            reader.close();

        } catch (FileNotFoundException e) {

            // File does not exist yet.
            // It will be created when saving.

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error checking department data.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Save new department
        try {

            FileWriter writer =
                    new FileWriter(FILE_NAME, true);

            writer.write(
                    departmentId + "|"
                    + departmentName + "|"
                    + description
            );

            writer.write(System.lineSeparator());

            writer.close();

            JOptionPane.showMessageDialog(
                    this,
                    "Department added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Clear fields after saving
            departmentIdField.setText("");
            departmentNameField.setText("");
            descriptionArea.setText("");

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error saving department.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


}