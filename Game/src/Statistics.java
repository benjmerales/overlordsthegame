public class Statistics {

    String statfor;
    int atk_gauge;
    int stand;
    int health;
    int energy;
    int[] atk_deck;
    int i_current_atk;
    int r_cards;

    public Statistics(String stat_goes_to, int init_attack_gauge, int init_standing, int init_health, int init_energy, int[] init_attack_deck, int init_index_current_attack, int init_remaining_cards) {
        this.statfor = stat_goes_to;
        this.atk_gauge = init_attack_gauge;
        this.stand = init_standing;
        this.health = init_health;
        this.energy = init_energy;
        this.atk_deck = init_attack_deck;
        this.i_current_atk = init_index_current_attack;
        this.r_cards = init_remaining_cards;
    }

    public Statistics(String stat_goes_to, int[] deck) {
        this.statfor = stat_goes_to;
        this.atk_gauge = 0;
        this.stand = 0;
        this.health = 20;
        this.energy = 5;
        this.atk_deck = deck;
        this.i_current_atk = 0;
        this.r_cards = 24;
    }

    /*
        public void setStatfor(String statfor) {
            this.statfor = statfor;
        }
    */
    public void setAttack_gauge(int attack_gauge) {
        this.atk_gauge = attack_gauge;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setAttack_deck(int[] attack_deck) {
        this.atk_deck = attack_deck;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setIndex_current_attack(int index_current_attack) {
        this.i_current_atk = index_current_attack;
    }

    public void setRemaining_cards(int remaining_cards) {
        this.r_cards = remaining_cards;
    }

    public void setStanding(int standing) {
        this.stand = standing;
    }

    public void changeAttackIndex(int index_to_edit, int value_to_change) {
        this.getAttack_deck()[index_to_edit] = value_to_change;
    }

    public String getStatfor() {
        return statfor;
    }

    public int getAttack_gauge() {
        return atk_gauge;
    }

    public int getEnergy() {
        return energy;
    }

    public int getHealth() {
        return health;
    }

    public int getIndex_current_attack() {
        return i_current_atk;
    }

    /*
    public int getRemaining_cards() {
        return r_cards;
    }*/
    public int getStanding() {
        return stand;
    }

    public int[] getAttack_deck() {
        return atk_deck;
    }

    public int getAtk_deckAtGivenIndex(int index) {
        return atk_deck[index];
    }

}
