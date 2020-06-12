import java.util.Random;
import java.util.Scanner;

/*
    Attack Gauge Class

        This class manages the attack gauges of a player

 */
public class AttackGauge {
    public static void main(String[] args) {
        int attack_gauge_player_1 = 0;
        int attack_gauge_player_2 = 0;

        int standing_player_1 = 0;
        int standing_player_2 = 0;

        int health_player_1 = 20;
        int health_player_2 = 20;

        int energy_player_1 = 5;
        int energy_player_2 = 5;

        int turn = 0;

        Scanner S = new Scanner(System.in);
        while (true) {
            if (turn % 2 == 0) {
                System.out.println("It is player ONE's turn");
            } else {
                System.out.println("It is player TWO's turn");
            }

            System.out.print("Hit or Stand: ");
            char choice = S.next().charAt(0);

            if (choice == 'H') {
                int card = new Random().nextInt(7);
                if (turn % 2 == 0) attack_gauge_player_1 += card;
                else attack_gauge_player_2 += card;
            } else if (choice == 'S') {
                if (turn % 2 == 0) standing_player_1 = 1;
                else standing_player_2 = 1;
                System.out.println("I stand my ground!");

            }
            System.out.print("My Current Attack Gauge is ");
            if (turn % 2 == 0) {
                System.out.println(attack_gauge_player_1);
                if (attack_gauge_player_1 > 12) {
                    System.out.println("WARNING! Overcharged");
                    attack_gauge_player_1 = attack_gauge_player_2 - 1;
                    standing_player_1 = 1;
                } else {
                    System.out.println(attack_gauge_player_2);
                    attack_gauge_player_2 = attack_gauge_player_1 - 1;
                    standing_player_2 = 1;
                }

            }
            if (standing_player_1 == 1 && standing_player_2 == 1) {
                System.out.println("Both Players have standed their ground! Calculating Battle Score.");
                if (attack_gauge_player_1 > attack_gauge_player_2) {
                    int strikes = attack_gauge_player_1 - attack_gauge_player_2;
                    System.out.println("Player One has won the Round!");
                    System.out.println("Player Two will then take " + strikes + " strikes from Player One");
                    health_player_2 -= strikes;

                } else if (attack_gauge_player_1 < attack_gauge_player_2) {
                    int strikes = attack_gauge_player_2 - attack_gauge_player_1;
                    System.out.println("Player Two has won the Round!");
                    System.out.println("Player One will then take " + strikes + "strikes from Player One");
                    health_player_1 -= strikes;
                } else {
                    System.out.println("Draw! Both Players will get five energy points");
                }
                if (health_player_1 == 0) System.out.println("Player 2 Wins!");
                if (health_player_2 == 0) System.out.println("Player 1 Wins!");

                turn += 1;

            }
        }
    }
}