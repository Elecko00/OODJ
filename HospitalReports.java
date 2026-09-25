import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class HospitalReports extends JFrame {

    private final String DEPARTMENT_FILE = "departments.txt";
    private final String ROSTER_FILE = "roster.txt";

    public HospitalReports() {

        setTitle("Hospital Reports");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(
                new BorderLayout(15, 15)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        // Title
        JLabel titleLabel = new JLabel(
                "Hospital Reports",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        JPanel summaryPanel = new JPanel(
                new GridLayout(1, 3, 15, 15)
        );

        JPanel departmentPanel =
                createSummaryPanel(
                        "Departments",
                        String.valueOf(
                                countRecords(DEPARTMENT_FILE)
                        )
                );

        JPanel doctorPanel =
                createSummaryPanel(
                        "Doctors",
                        String.valueOf(
                                countUniqueDoctors()
                        )
                );

        JPanel rosterPanel =
                createSummaryPanel(
                        "Roster Records",
                        String.valueOf(
                                countRecords(ROSTER_FILE)
                        )
                );

        summaryPanel.add(departmentPanel);
        summaryPanel.add(doctorPanel);
        summaryPanel.add(rosterPanel);

        JPanel contentPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        contentPanel.add(
                summaryPanel,
                BorderLayout.NORTH
        );

        JLabel rosterLabel = new JLabel(
                "Roster Summary"
        );

        rosterLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        contentPanel.add(
                rosterLabel,
                BorderLayout.CENTER
        );

        String[] columnNames = {
                "Doctor ID",
                "Doctor Name",
                "Date",
                "Shift"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columnNames,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        loadRosterData(model);

        JTable table = new JTable(model);

        table.setRowHeight(30);

        table.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        JPanel tablePanel = new JPanel(
                new BorderLayout(5, 5)
        );

        tablePanel.add(
                rosterLabel,
                BorderLayout.NORTH
        );

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        contentPanel.add(
                tablePanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        JButton closeButton =
                new JButton("Close");

        closeButton.addActionListener(e -> {
            dispose();
        });

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(closeButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        setVisible(true);
    }

    private JPanel createSummaryPanel(
            String title,
            String value
    ) {

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        title
                )
        );

        JLabel valueLabel = new JLabel(
                value,
                SwingConstants.CENTER
        );

        valueLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        panel.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return panel;
    }

    private int countRecords(
            String fileName
    ) {

        int count = 0;

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(fileName)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    count++;
                }
            }

            reader.close();

        } catch (IOException ex) {

            return 0;
        }

        return count;
    }

    private int countUniqueDoctors() {

        ArrayList<String> doctorIds =
                new ArrayList<>();

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(ROSTER_FILE)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        line.split("\\|", -1);

                if (data.length == 4) {

                    if (!doctorIds.contains(data[0])) {

                        doctorIds.add(data[0]);
                    }
                }
            }

            reader.close();

        } catch (IOException ex) {

            return 0;
        }

        return doctorIds.size();
    }

    private void loadRosterData(
            DefaultTableModel model
    ) {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(ROSTER_FILE)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data =
                        line.split("\\|", -1);

                if (data.length == 4) {

                    model.addRow(
                            new Object[]{
                                    data[0],
                                    data[1],
                                    data[2],
                                    data[3]
                            }
                    );
                }
            }

            reader.close();

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error reading roster file."
            );
        }
    }
}