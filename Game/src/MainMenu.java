import javax.swing.*;


public class MainMenu extends JFrame {
    private JPanel panel1;
    private JButton startButton;
    private JButton howToPlayButton;
    private JButton exitButton;
    private JButton creditsButton;

    public MainMenu(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        // Removes Highlighted Button on Pop Up
        startButton.setFocusPainted(false);
        howToPlayButton.setFocusPainted(false);
        creditsButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        startButton.addActionListener(e -> {
            ChampionSelection frame = new ChampionSelection("Champion Selection");
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
            dispose();
        });
        howToPlayButton.addActionListener(e -> {
            HowToPlay frame = new HowToPlay("How To Play");
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
            dispose();
        });
        creditsButton.addActionListener(e -> {
            Credits frame = new Credits("Credits");
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
            dispose();
        });
        exitButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Thank you for playing Overlords!");
                setVisible(false);
                System.exit(0);
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new MainMenu("Overlords");
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
