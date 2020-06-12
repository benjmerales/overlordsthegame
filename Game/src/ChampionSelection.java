import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChampionSelection extends JFrame {
    private JPanel Heroes;
    private JButton knightButton;
    private JButton mageButton;
    private JButton mechanicButton;

    String P1_champion, P2_champion;

    public ChampionSelection(String Title) {
        super(Title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(Heroes);
        this.pack();

        knightButton.setBorder(null);
        mageButton.setBorder(null);
        mechanicButton.setBorder(null);


        knightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (P1_champion == null) {
                    JOptionPane.showMessageDialog(null, "Player One Has Chosen Knight");
                    P1_champion = "Knight";
                    JOptionPane.showMessageDialog(null, "Player 2, choose a class...");
                } else {
                    JOptionPane.showMessageDialog(null, "Player Two Has Chosen Knight");
                    P2_champion = "Knight";
                }
                knightButton.setEnabled(false);
                isBothSelected();
            }
        });
        mageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (P1_champion == null) {
                    JOptionPane.showMessageDialog(null, "Player One Has Chosen Mage");
                    P1_champion = "Mage";
                    JOptionPane.showMessageDialog(null, "Player 2, choose a class...");
                } else {
                    JOptionPane.showMessageDialog(null, "Player Two Has Chosen Mage");
                    P2_champion = "Mage";
                }
                mageButton.setEnabled(false);
                isBothSelected();
            }
        });
        mechanicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (P1_champion == null) {
                    JOptionPane.showMessageDialog(null, "Player One Has Chosen Mechanic");
                    P1_champion = "Mechanic";
                    JOptionPane.showMessageDialog(null, "Player 2, choose a class...");
                } else {
                    JOptionPane.showMessageDialog(null, "Player Two Has Chosen Mechanic");
                    P2_champion = "Mechanic";
                }
                mechanicButton.setEnabled(false);
                isBothSelected();
            }
        });
    }

    public void isBothSelected() {
        if (P1_champion != null && P2_champion != null) {
            JOptionPane.showMessageDialog(null, "Both Players Have Now Selected");
            JOptionPane.showMessageDialog(null, P1_champion + " VS " + P2_champion);
            BattleLayout newFrame = new BattleLayout("Battle Phase", P1_champion, P2_champion);
            newFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            newFrame.setVisible(true);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Player 1, choose a class...");
        JFrame frame = new ChampionSelection("Champions");
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

}
