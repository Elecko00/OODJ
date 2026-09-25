import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class DeleteRoster extends JFrame {

    private JTextField doctorIdField;
    private JTextField doctorNameField;
    private JTextField dateField;
    private JTextField shiftField;

    private final String FILE_NAME = "data/roster.txt";

    public DeleteRoster() {

        setTitle("Delete Doctor Roster");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel doctorIdLabel = new JLabel("Doctor ID:");
        doctorIdField = new JTextField();

        JLabel doctorNameLabel = new JLabel("Doctor Name:");
        doctorNameField = new JTextField();

        JLabel dateLabel = new JLabel("Date:");
        dateField = new JTextField();

        JLabel shiftLabel = new JLabel("Shift:");
        shiftField = new JTextField();

        JButton searchButton = new JButton("Search");
        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        panel.add(doctorIdLabel);
        panel.add(doctorIdField);

        panel.add(doctorNameLabel);
        panel.add(doctorNameField);

        panel.add(dateLabel);
        panel.add(dateField);

        panel.add(shiftLabel);
        panel.add(shiftField);

        panel.add(searchButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(cancelButton);

        add(bottomPanel, BorderLayout.SOUTH);

        doctorNameField.setEditable(false);
        dateField.setEditable(false);
        shiftField.setEditable(false);

        deleteButton.setEnabled(false);

        searchButton.addActionListener(e -> {

            String doctorId = doctorIdField.getText().trim();

            if (doctorId.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Doctor ID."
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

                    if (data.length == 4
                            && data[0].equals(doctorId)) {

                        doctorNameField.setText(data[1]);
                        dateField.setText(data[2]);
                        shiftField.setText(data[3]);

                        found = true;

                        deleteButton.setEnabled(true);

                        break;
                    }
                }

                reader.close();

                if (!found) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Doctor roster not found."
                    );

                    doctorNameField.setText("");
                    dateField.setText("");
                    shiftField.setText("");

                    deleteButton.setEnabled(false);
                }

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error reading roster file."
                );
            }
        });

        deleteButton.addActionListener(e -> {

            String doctorId = doctorIdField.getText().trim();

            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this roster?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmation != JOptionPane.YES_OPTION) {
                return;
            }

            try {

                ArrayList<String> rosters = new ArrayList<>();

                BufferedReader reader =
                        new BufferedReader(new FileReader(FILE_NAME));

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] data = line.split("\\|", -1);

                    if (data.length == 4
                            && data[0].equals(doctorId)) {

                        continue;
                    }

                    rosters.add(line);
                }

                reader.close();

                BufferedWriter writer =
                        new BufferedWriter(new FileWriter(FILE_NAME));

                for (String roster : rosters) {

                    writer.write(roster);
                    writer.newLine();
                }

                writer.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Roster deleted successfully!"
                );

                doctorIdField.setText("");
                doctorNameField.setText("");
                dateField.setText("");
                shiftField.setText("");

                deleteButton.setEnabled(false);

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error deleting roster."
                );
            }
        });

        cancelButton.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }
}