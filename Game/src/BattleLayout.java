import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.util.Random;

public class BattleLayout extends JFrame {
    private JPanel panel1;
    private JButton CardA;
    private JButton CardC;
    private JButton CardB;
    private JButton Hit;
    private JButton Stand;
    private JLabel OtherHealth;
    private JLabel OtherEnergy;
    private JLabel OtherPlayer;
    private JLabel OtherGauge;
    private JLabel Gauge;
    private JPanel myPanel;
    private JLabel Health;
    private JLabel Energy;
    private JLabel Drawn;

    int TURN;
    String FILEPATH = System.getProperty("user.dir");

    int[] P1Deck = new InitializeDeck().getDeck();
    int[] P2Deck = new InitializeDeck().getDeck();

    Statistics P1Stat = new Statistics("P1", P1Deck);
    Statistics P2Stat = new Statistics("P2", P2Deck);

    Statistics Stat;
    Statistics otherStat;

    ChampionCards P1CC;
    ChampionCards P2CC;
    ChampionCards thisCC;

    CardProperties cardProperties = new CardProperties();

    public BattleLayout(String title, String P1C, String P2C) {
        super(title);
        this.setContentPane(myPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.TURN = 0;
        this.P1CC = new ChampionCards(P1C);
        this.P2CC = new ChampionCards(P2C);

        Sound.BACK.loop();
        CardA.setFocusPainted(false); // Disables Focus on first button since it is shown on startup.

        UIManager UI = new UIManager();
        UI.put("OptionPane.background", new ColorUIResource(33, 147, 255));
        UI.put("Panel.background", new ColorUIResource(33, 147, 255));
        UI.put("OptionPane.messageForeground", Color.WHITE);
        UI.put("OptionPane.button", Color.RED);

        InitializeField();
        panel1.setForeground(Color.PINK); // So we could use the panel and to not make it never used. I don't know how this works either.
        Hit.addActionListener(e -> {
            checkTurn();
            addATK_Gauge();
            checkEmptyDeck();
            // when player overcharges, battle phase immediately ends
            if (Stat.getAttack_gauge() > 12) {
                overchargingPhase();
                calculateBattlePhase();
                resetGauges();
                changeField();
            } else {
                if (otherStat.getStanding() == 0) {
                    nextTurn();
                    changeField();
                    checkTurn();
                    promptTurn(1);
                    changeChampionCards();
                    changeField();
                } else if (otherStat.getStanding() == 1) {
                    changeField();
                    promptTurn(2);
                }
            }
        });
        Stand.addActionListener(e -> {
            checkTurn();
            Stat.stand = 1;
            checkStand();
            nextTurn();
            checkTurn();
            changeChampionCards();
            changeField();
        });

        JButton[] championCards = {CardA, CardB, CardC};
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            championCards[i].addActionListener(e -> {
                checkTurn();
                cardEffects(finalI);
            });
        }
    }

    // Simple Method that Increments the TURN variable, which determines the player's turn.
    public void nextTurn() {
        TURN++;
    }

    // Initialize Field will only be called ONCE, which is on the constructor.
    public void InitializeField() {
        CardA.setText(P1CC.getPlayerChampionCards()[0]);
        CardB.setText(P1CC.getPlayerChampionCards()[1]);
        CardC.setText(P1CC.getPlayerChampionCards()[2]);

        OtherHealth.setText(P2Stat.getHealth() + "");
        OtherEnergy.setText(P2Stat.getEnergy() + "");
        OtherGauge.setText(P2Stat.getAttack_gauge() + "");

        Health.setText(P1Stat.getHealth() + "");
        Energy.setText(P1Stat.getEnergy() + "");
        Gauge.setText(P1Stat.getAttack_gauge() + "");

        //IMAGES INSERTED HERE IN CASE OF ERROR
        Gauge.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Attack Gauge\\" + P1Stat.getAttack_gauge() + ".png"));
        OtherGauge.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Attack Gauge\\" + P2Stat.getAttack_gauge() + ".png"));
        OtherPlayer.setText("P2: " + P2CC.getPlayerChampion());
        if(P2CC.getPlayerChampion() == "Knight")
            OtherPlayer.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\The Knight.gif"));
        else if(P2CC.getPlayerChampion() == "Mage")
            OtherPlayer.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\The Mage.gif"));
        else if(P2CC.getPlayerChampion() == "Mechanic")
            OtherPlayer.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\The Mechanic.gif"));
        else
            System.out.println("ERROR on Initialize Field Setting Other Player");
    }

    // Checks If The AttackDeck is empty. If it is empty, the class resets and gives another set of random cards from 1-6
    public void checkEmptyDeck() {
        // if the player uses all 24 cards, then we initialize a new random deck
        if (Stat.i_current_atk == Stat.atk_deck.length) {
            //NOTE: Before I made the message dialog "OTHERSTAT" instead of "STAT" I still don't know why
            JOptionPane.showMessageDialog(null, Stat.getStatfor() + " has ran out of cards in his attack deck. Creating a new one.");
            Stat.setAttack_deck(new InitializeDeck().getDeck());
            Stat.setRemaining_cards(24);
            Stat.setIndex_current_attack(0);
        }
    }

    // Executes when an Overcharging status occurs. It manipulates both the statistics of the players (i.e. Attack gauge, and Health).
    public void overchargingPhase() {
        // if current player overcharges, battle phase immediately ends
        JOptionPane.showMessageDialog(null, Stat.getStatfor() + " has overcharged!");
        Stat.setStanding(1);
        // nextTurn();
        if (otherStat.getAttack_gauge() >= 6) {
            Stat.atk_gauge = 6;
        } else {
            Stat.atk_gauge = otherStat.atk_gauge - 1;
        }
        if (TURN % 2 == 0) {
            Stat = P1Stat;
            otherStat = P2Stat;
        } else {
            Stat = P2Stat;
            otherStat = P1Stat;
        }
    }

    // Checks whose turn is it, determining whose Statistics are to be used and whose Statistics are on standby. Also gets the Cards of the current player
    public void checkTurn() {

        if (TURN % 2 == 0) {
            Stat = P1Stat;
            otherStat = P2Stat;
            thisCC = P1CC;
        } else {
            Stat = P2Stat;
            otherStat = P1Stat;
            thisCC = P2CC;
        }
    }

    // Prompts using message dialog whose turn is it to avoid confusion
    public void promptTurn(int i) {
        if (i == 1)
            JOptionPane.showMessageDialog(null, "It is now Player " + ((TURN % 2) + 1) + "'s Turn. ");
        else if (i == 2)
            JOptionPane.showMessageDialog(null, "It is still your turn, Player " + ((TURN % 2) + 1));
        Drawn.setText("Drawn Card");
        Drawn.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Values\\H.png"));
    }

    // Method executes when a battle phase was ended. In other words, the method will reset both attack gauges of both players
    public void resetGauges() {
        Stat.setAttack_gauge(0);
        otherStat.setAttack_gauge(0);

        Gauge.setText(0 + "");
        OtherGauge.setText(0 + "");

        //IMAGES INSERTED HERE IN CASE OF ERROR
        Gauge.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Attack Gauge\\0.png"));
        OtherGauge.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Attack Gauge\\0.png"));
    }

    // This is where computations starts. Determines who wins in the current round and reduces
    public void calculateBattlePhase() {
        if (Stat.atk_gauge > otherStat.atk_gauge) {
            int strikes = Stat.atk_gauge - otherStat.atk_gauge;
            JOptionPane.showMessageDialog(null, Stat.getStatfor() + " has won the battle!");
            if (isEncouraged) {
                JOptionPane.showMessageDialog(null, "Encourage was used");
                strikes += (int) Math.ceil((double) strikes * 0.20);
                isEncouraged = false;
            }
            JOptionPane.showMessageDialog(null, otherStat.getStatfor() + " will take " + strikes + " strikes from " + Stat.getStatfor());
            otherStat.health = otherStat.health - strikes;
        } else if (Stat.atk_gauge < otherStat.atk_gauge) {
            int strikes = otherStat.atk_gauge - Stat.atk_gauge;
            JOptionPane.showMessageDialog(null, otherStat.getStatfor() + " has won the battle!");
            if (isBlocked) {
                JOptionPane.showMessageDialog(null, "A Block was Used.");
                JOptionPane.showMessageDialog(null, "Strikes before: " + strikes);
                strikes -= 2;
                isBlocked = false;
                JOptionPane.showMessageDialog(null, "Strikes now: " + strikes);
            }
            JOptionPane.showMessageDialog(null, Stat.getStatfor() + " will take " + strikes + " strikes from " + otherStat.getStatfor());
            Stat.health = Stat.health - strikes;
        } else {
            JOptionPane.showMessageDialog(null, "Battle was a draw! Both will get 3 energy points");
            Stat.setEnergy(Stat.getEnergy() + 3);
            otherStat.setEnergy(otherStat.getEnergy() + 3);
        }
        resetCC();

        checkGameOver();
        nextTurn();
    }

    // UI-related method that changes the cards buttons to the current player turn/
    public void changeChampionCards() {
        ChampionCards thisCC;
        if (TURN % 2 == 0) thisCC = P1CC;
        else thisCC = P2CC;

        CardA.setText(thisCC.getPlayerChampionCards()[0]);
        CardB.setText(thisCC.getPlayerChampionCards()[1]);
        CardC.setText(thisCC.getPlayerChampionCards()[2]);

        CardA.setToolTipText(thisCC.getPlayerTTCards()[0]);
        CardB.setToolTipText(thisCC.getPlayerTTCards()[1]);
        CardC.setToolTipText(thisCC.getPlayerTTCards()[2]);

    }

    // Resets both the standing boolean variables of both players to false.
    public void resetCC() {
        Stat.setStanding(0);
        otherStat.setStanding(0);
    }

    // UI-related method that changes everything in the panel, more specifically on the statistics.
    public void changeField() {

        Health.setText(Stat.getHealth() + "");
        Energy.setText(Stat.getEnergy() + "");
        Gauge.setText(Stat.getAttack_gauge() + "");
        updateHealth();
        updateEnergy();

        OtherHealth.setText(otherStat.getHealth() + "");
        OtherGauge.setText(otherStat.getAttack_gauge() + "");
        OtherEnergy.setText(otherStat.getEnergy() + "");

        OtherPlayer.setText("Opponent: " + thisCC.getPlayerChampion() + "");
        if(thisCC.getPlayerChampion() == "Knight")
            OtherPlayer.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\The Knight.gif"));
        else if(thisCC.getPlayerChampion() == "Mage")
            OtherPlayer.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\The Mage.gif"));
        else if(thisCC.getPlayerChampion() == "Mechanic")
            OtherPlayer.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\The Mechanic.gif"));
        else
            System.out.println("ERROR on Initialize Field Setting Other Player");
    }

    // Checks if the game is over.
    public void checkGameOver() {
        checkTurn();
        if (Stat.health < 0) {
            JOptionPane.showMessageDialog(null, Stat.getStatfor() + " wins! Congratulations!");
        }
        if (otherStat.health < 0) {
            JOptionPane.showMessageDialog(null, otherStat.getStatfor() + " wins! Congratulations!");
        }
    }

    // Checks if both players have made a stand. If it is true, then the method proceeds to execute the battlephase.
    public void checkStand() {
        if (Stat.stand == 1 && otherStat.stand == 1) {
            JOptionPane.showMessageDialog(null, "Both Player Have Stand Their Grounded! Calculating Battle Score");
            calculateBattlePhase();
            resetGauges();
        } else {
            JOptionPane.showMessageDialog(null, "It is now Player " + ((TURN % 2) + 1) + "'s Turn. ");
        }
    }

    // Basic method that adds any values to the player's attack gauge. Also tries to check for overcharging when the attack gauge fills to above 12.
    public void addATK_Gauge() {
        int atk = Stat.atk_deck[Stat.i_current_atk];
        Stat.i_current_atk++;
        Stat.r_cards--;
        Stat.atk_gauge += atk;

        if (isReversed) {
            int toBeReduced = atk / 2;
            JOptionPane.showMessageDialog(null, "Since you dealt " + atk + ", You reversed the opponent's attack gauge by " + toBeReduced);
            otherStat.atk_gauge = otherStat.atk_gauge - toBeReduced;
            isReversed = false;
        }
        if (Stat.getAttack_gauge() > 12) {
            if (isPrevent) {
                JOptionPane.showMessageDialog(null, "You overcharged. Recharging...");
                isPrevent = false;
                Stat.atk_gauge -= atk;
            }
        }
        Drawn.setText(atk + "");
        Drawn.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Values\\" + atk + ".png"));
    }

    // UI Icon Updates in changing Health and Energy Values
    public void updateEnergy(int i){ Energy.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Energy\\" + i + ".png")); }
    public void updateEnergy(){ Energy.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Energy\\" + Stat.getEnergy() + ".png")); }
    public void updateHealth(int i){ Health.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Health\\" + i + ".png")); }
    public void updateHealth(){ Health.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Health\\" + Stat.getHealth() + ".png")); }
    public void updateGauge(int i) { Gauge.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Attack Gauge\\" + i + ".png" )); }
    public void updateOtherGauge(int i) { OtherGauge.setIcon(new ImageIcon(FILEPATH + "\\src\\Sprites\\Attack Gauge\\" + i + ".png" )); }
    public boolean checkEnergy(int i){
        if(i <= Stat.getEnergy()){
            Stat.setEnergy(Stat.getEnergy() - i);
            updateEnergy(Stat.getEnergy());
            return true;
        }
        JOptionPane.showMessageDialog(null, "You don't have enough energy to use this skill.");
        return false;
    }

    boolean isEncouraged = false, isPrevent = false, isBlocked = false, isReversed = false; // Instance variables for Card Effects

    // Contains all of the Players Champion Card Effects
    public void cardEffects(int finalI) {
        if (finalI == 0) {
            switch (thisCC.getPlayerChampion()) {
                case "Knight":  // Encourage
                    if(checkEnergy(1)) {

                        JOptionPane.showMessageDialog(null, "You strengthened yourself");
                        isEncouraged = cardProperties.initEncourage(Stat);
                    }
                    break;
                case "Mage":  // Clairvoyance
                    if(checkEnergy(2)) {
                        JOptionPane.showMessageDialog(null, "You foresee the top 3 cards on your deck");
                        cardProperties.initClairvoyance(Stat);
                    }
                    break;
                case "Mechanic":  // Tinker
                    if(checkEnergy(2)) {
                        JOptionPane.showMessageDialog(null, "Tinkering...");
                        Random R = new Random();
                        int tinker_value = R.nextInt(3) + 1;
                        JOptionPane.showMessageDialog(null, "Mechanic made a " + tinker_value);
                        int returnValue = JOptionPane.showConfirmDialog(null, "Use?");
                        if (returnValue == JOptionPane.YES_OPTION) {
                            cardProperties.initTinker(tinker_value, Stat);
                            changeField();
                        } else {
                            JOptionPane.showMessageDialog(null, "You canceled your Tinkered Card");
                        }
                    }
                    break;
            }
        } else if (finalI == 1) {
            switch (thisCC.getPlayerChampion()) {
                case "Knight":  // Block
                    if(checkEnergy(1)) {
                        JOptionPane.showMessageDialog(null, "You used your Shield!");
                        isBlocked = cardProperties.initBlock();
                    }
                    break;
                case "Mage":   // Meteor
                    if(checkEnergy(3)) {
                        JOptionPane.showMessageDialog(null, "You casted Meteor");
                        cardProperties.initMeteor(otherStat);
                        checkGameOver();
                        changeField();
                    }
                    break;
                case "Mechanic":  // Howitzer
                    if(checkEnergy(2)) {
                        JOptionPane.showMessageDialog(null, "You Pulled out the Howitzer");
                        Random R = new Random();
                        int chance = Math.round(R.nextInt(2));
                        if (chance == 1) {
                            JOptionPane.showMessageDialog(null, "The Howitzer succeeded and you gained 10 ATK points");
                            cardProperties.initHowitzer(10, Stat);
                        } else {
                            JOptionPane.showMessageDialog(null, "The Howitzer failed! You gained 1 ATK points");
                            cardProperties.initHowitzer(1, Stat);
                        }
                        changeField();
                    }
                    break;
            }
        } else if (finalI == 2) {
            switch (thisCC.getPlayerChampion()) {
                case "Knight":  // Daze
                    if(checkEnergy(2)) {
                        JOptionPane.showMessageDialog(null, "You dazed the player! ");
                        cardProperties.initDaze(otherStat);
                    }
                    break;
                case "Mage":  // Reverse
                    if(checkEnergy(1)) {
                        JOptionPane.showMessageDialog(null, "You reversed the player's time!");
                        if (cardProperties.initReverse()) {
                            isReversed = true;
                        }
                    }
                    break;
                case "Mechanic":  // Prevent
                    if(checkEnergy(2)) {
                        JOptionPane.showMessageDialog(null, "You won't overcharged once");
                        isPrevent = cardProperties.initPrevent();
                    }
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error on Card Effects");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new BattleLayout("test", "Knight", "Mage");
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
