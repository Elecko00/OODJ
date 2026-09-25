import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InsuranceManagementFrame extends JFrame {

    public InsuranceManagementFrame() {

        setTitle("HMS - Insurance Management");
        setSize(300, 280);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title =
                new JLabel("Insurance Management");

        title.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        title.setBounds(17, 15, 250, 25);

        JButton addButton =
                new JButton("Add Insurance");

        JButton viewButton =
                new JButton("View Insurance");

        JButton updateButton =
                new JButton("Update Insurance");

        JButton deleteButton =
                new JButton("Delete Insurance");

        addButton.setBounds(17, 50, 250, 30);
        viewButton.setBounds(17, 90, 250, 30);
        updateButton.setBounds(17, 130, 250, 30);
        deleteButton.setBounds(17, 170, 250, 30);

        panel.add(title);
        panel.add(addButton);
        panel.add(viewButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(panel);

        addButton.addActionListener(
                e -> addInsurance()
        );

        viewButton.addActionListener(
                e -> viewInsurance()
        );

        updateButton.addActionListener(
                e -> updateInsurance()
        );

        deleteButton.addActionListener(
                e -> deleteInsurance()
        );
    }

    private void addInsurance() {

        String name =
                JOptionPane.showInputDialog(
                        this,
                        "Insurance name:",
                        "Add Insurance",
                        JOptionPane.QUESTION_MESSAGE
                );

        if (name == null) {
            return;
        }

        int validity =
                JOptionPane.showConfirmDialog(
                        this,
                        "Is this insurance valid?",
                        "Insurance Validity",
                        JOptionPane.YES_NO_OPTION
                );

        boolean valid =
                validity == JOptionPane.YES_OPTION;

        ModifyInsurance modify =
                new ModifyInsurance();

        int id =
                modify.returnLatestInsuranceId() + 1;

        String result =
                modify.writeInsurance(
                        id,
                        name.trim(),
                        valid,
                        "add"
                );

        JOptionPane.showMessageDialog(
                this,
                result
        );
    }

    private void viewInsurance() {

        LoadInsurance loader =
                new LoadInsurance();

        loader.readInsurance();

        List<Insurance> list =
                loader.returnInsurance();

        StringBuilder result =
                new StringBuilder();

        for (Insurance insurance : list) {

            result.append("ID: ")
                    .append(
                            insurance.returnInsuranceId()
                    )
                    .append("\n");

            result.append("Name: ")
                    .append(
                            insurance.returnInsuranceName()
                    )
                    .append("\n");

            result.append("Valid: ")
                    .append(
                            insurance.returnInsuranceValidity()
                    )
                    .append("\n");

            result.append("--------------------\n");
        }

        JTextArea area =
                new JTextArea(result.toString());

        area.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(area);

        scroll.setPreferredSize(
                new Dimension(330, 230)
        );

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Insurance List",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void updateInsurance() {

        String idText =
                JOptionPane.showInputDialog(
                        this,
                        "Insurance ID:",
                        "Update Insurance",
                        JOptionPane.QUESTION_MESSAGE
                );

        if (idText == null) {
            return;
        }

        try {

            int id =
                    Integer.parseInt(idText.trim());

            String name =
                    JOptionPane.showInputDialog(
                            this,
                            "New insurance name:",
                            "Update Insurance",
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (name == null) {
                return;
            }

            int validity =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Is insurance valid?",
                            "Insurance Validity",
                            JOptionPane.YES_NO_OPTION
                    );

            boolean valid =
                    validity == JOptionPane.YES_OPTION;

            ModifyInsurance modify =
                    new ModifyInsurance();

            String result =
                    modify.writeInsurance(
                            id,
                            name.trim(),
                            valid,
                            "update"
                    );

            JOptionPane.showMessageDialog(
                    this,
                    result
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Insurance ID."
            );
        }
    }

    private void deleteInsurance() {

        String idText =
                JOptionPane.showInputDialog(
                        this,
                        "Insurance ID:",
                        "Delete Insurance",
                        JOptionPane.QUESTION_MESSAGE
                );

        if (idText == null) {
            return;
        }

        try {

            int id =
                    Integer.parseInt(idText.trim());

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Delete Insurance ID " + id + "?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm ==
                    JOptionPane.YES_OPTION) {

                ModifyInsurance modify =
                        new ModifyInsurance();

                String result =
                        modify.writeInsurance(
                                id,
                                "",
                                false,
                                "delete"
                        );

                JOptionPane.showMessageDialog(
                        this,
                        result
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Insurance ID."
            );
        }
    }
}