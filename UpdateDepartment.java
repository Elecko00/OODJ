import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class UpdateDepartment extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextArea descriptionArea;

    private final String FILE_NAME = "departments.txt";

    public UpdateDepartment() {

        setTitle("Update Department");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel idLabel = new JLabel("Department ID:");
        idField = new JTextField();

        JLabel nameLabel = new JLabel("Department Name:");
        nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);

        JButton searchButton = new JButton("Search");
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        idField.setEditable(true);

        panel.add(idLabel);
        panel.add(idField);

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(descriptionLabel);
        panel.add(new JScrollPane(descriptionArea));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        nameField.setEditable(false);
        descriptionArea.setEditable(false);
        saveButton.setEnabled(false);

        // Search Department
        searchButton.addActionListener(e -> {

            String departmentId = idField.getText().trim();

            if (departmentId.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Department ID."
                );
                return;
            }

            try {

                BufferedReader reader =
                        new BufferedReader(new FileReader(FILE_NAME));

                String line;
                boolean found = false;

                while ((line = reader.readLine()) != null) {

                    String[] data = line.split("\\|", -1);

                    if (data.length == 3 &&
                            data[0].equals(departmentId)) {

                        nameField.setText(data[1]);
                        descriptionArea.setText(data[2]);

                        found = true;

                        nameField.setEditable(true);
                        descriptionArea.setEditable(true);
                        saveButton.setEnabled(true);

                        break;
                    }
                }

                reader.close();

                if (!found) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Department ID not found."
                    );

                    nameField.setText("");
                    descriptionArea.setText("");

                    nameField.setEditable(false);
                    descriptionArea.setEditable(false);
                    saveButton.setEnabled(false);
                }

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error reading department file."
                );
            }
        });

        saveButton.addActionListener(e -> {

            String departmentId = idField.getText().trim();
            String departmentName = nameField.getText().trim();
            String description = descriptionArea.getText().trim();

            if (departmentName.isEmpty() || description.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in all fields."
                );

                return;
            }

            try {

                ArrayList<String> departments = new ArrayList<>();

                BufferedReader reader =
                        new BufferedReader(new FileReader(FILE_NAME));

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] data = line.split("\\|", -1);

                    if (data.length == 3 &&
                            data[0].equals(departmentId)) {

                        departments.add(
                                departmentId + "|" +
                                departmentName + "|" +
                                description
                        );

                    } else {

                        departments.add(line);
                    }
                }

                reader.close();

                BufferedWriter writer =
                        new BufferedWriter(new FileWriter(FILE_NAME));

                for (String department : departments) {

                    writer.write(department);
                    writer.newLine();
                }

                writer.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Department updated successfully!"
                );

                nameField.setEditable(false);
                descriptionArea.setEditable(false);
                saveButton.setEnabled(false);

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error updating department."
                );
            }
        });

        // Cancel
        cancelButton.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }
}