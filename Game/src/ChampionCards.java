public class ChampionCards {
    String[] KnightCards = {"Encourage", "Block", "Daze"};
    String[] MageCards = {"Clairvoyance", "Meteor", "Reverse"};
    String[] MechanicCards = {"Tinker", "Howitzer", "Recharge"};

    String[] ToolTipKnight = { "Increases Attack Damage Slightly", "Increases Defense Slightly", "The Next Card of the Opponent will be doubled"};
    String[] ToolTipMage = {"See the top 3 cards on your deck", "Deals Moderate Damage", "Reverses"};
    String[] ToolTipMechanic = {"Rolls 1-3 ATK", "Rolls 1 or 10 ATK", "You won't get overcharged once"};
    String[] playerChampionCards;
    String[] toolTipCards;
    String yourChampion;

    public ChampionCards(String Champion) {
        this.yourChampion = Champion;
        if (Champion == "Knight") {
            this.playerChampionCards = KnightCards;
            this.toolTipCards = ToolTipKnight;
        } else if (Champion == "Mage") {
            this.playerChampionCards = MageCards;
            this.toolTipCards = ToolTipMage;
        } else if (Champion == "Mechanic") {
            this.toolTipCards = ToolTipMechanic;
            this.playerChampionCards = MechanicCards;
        } else {
            System.out.println("An ERROR occured on ChampionCards Class...");
        }
    }

    public String[] getPlayerChampionCards() {
        return playerChampionCards;
    }
    public String[] getPlayerTTCards() { return toolTipCards; }

    public String getPlayerChampion() {
        return yourChampion;
    }
}

