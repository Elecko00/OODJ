import javax.swing.*;
import java.awt.*;

public class DepartmentManagement extends JFrame {

    private String managerId;

    public DepartmentManagement(String managerId) {

        this.managerId = managerId;

        setTitle("Clinical Department Management");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton addButton = new JButton("Add Department");
        JButton viewButton = new JButton("View Departments");
        JButton updateButton = new JButton("Update Department");
        JButton backButton = new JButton("Back");

       addButton.addActionListener(e -> {
            new AddDepartment();
        });

        viewButton.addActionListener(e -> {
            new ViewDepartment();
        });

        updateButton.addActionListener(e -> {
            new UpdateDepartment();
        });

        backButton.addActionListener(e -> {
            dispose();
        });

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(updateButton);
        panel.add(backButton);

        add(panel);

        setVisible(true);
    }
} 
    

