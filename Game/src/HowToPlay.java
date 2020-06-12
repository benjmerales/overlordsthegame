import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HowToPlay extends JFrame {
    private JButton back;
    private JPanel panel1;
    private JTextPane howToPlayTheTextPane;

    public HowToPlay(String title) {

        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MainMenu menu = new MainMenu("Main Menu");
                menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
                menu.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new HowToPlay("instructions");
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

}

