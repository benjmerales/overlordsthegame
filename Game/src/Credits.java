import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Credits extends JFrame {
    private JButton back;
    private JPanel panel1;
    private JTextPane developersBenJulianMeralesTextPane;


    public Credits(String title) {

        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MainMenu start = new MainMenu("Main Menu");
                start.setExtendedState(JFrame.MAXIMIZED_BOTH);
                start.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new Credits("Credits");
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

}
