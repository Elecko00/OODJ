
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AdminPage extends JFrame {

    private Admin admin;
    private JPanel sidebarPanel;
    private JPanel contentPanel;

    public AdminPage(Admin admin) {
        this.admin = admin;

        setTitle("Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 400));
        setSize(900, 600);
        setLocationRelativeTo(null);

        setLayout(null);

        this.sidebarPanel = new JPanel();
        this.sidebarPanel.setBackground(Color.decode("#27374D"));

        this.contentPanel = new JPanel();
        this.contentPanel.setBackground(Color.decode("#526D82"));

        add(this.sidebarPanel);
        add(this.contentPanel);

        this.addComponentListener(new ComponentAdapter() {
            @Override 
            public void componentResized(ComponentEvent e) {
                int frameWidth = getContentPane().getWidth();
                int frameHeight = getContentPane().getHeight();

                int sidebarWidth = (int) (frameWidth * 0.30);
                int contentWidth = frameWidth - sidebarWidth;

                sidebarPanel.setBounds(0, 50, sidebarWidth, frameHeight);
                contentPanel.setBounds(sidebarWidth, 50 , contentWidth, frameHeight);

                revalidate();
                repaint();
            }
        });
    }
}