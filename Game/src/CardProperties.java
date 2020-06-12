import javax.swing.*;

public class CardProperties {

    public boolean initBlock() {
        return true;
    }

    public boolean initEncourage(Statistics myStat) {
        return true;
    }

    public void initDaze(Statistics otherStat) {
        int index = otherStat.getIndex_current_attack();
        otherStat.changeAttackIndex(
                index, // index
                otherStat.getAtk_deckAtGivenIndex(index) * 2 // element on given index times two
        );
    }


    public void initTinker(int modifier, Statistics myDeck) {
        myDeck.atk_gauge += modifier;
    }

    public boolean initPrevent() {
        return true;
    }

    public void initHowitzer(int modifier, Statistics myDeck) {
        myDeck.atk_gauge += modifier;
    }

    public void initMeteor(Statistics stat) {
        stat.setHealth(stat.getHealth() - 5);
    }

    public boolean initReverse() {
        return true;
    }

    public void initClairvoyance(Statistics myDeck) {
        int[] first3Cards = {myDeck.getAtk_deckAtGivenIndex(myDeck.getIndex_current_attack()),
                myDeck.getAtk_deckAtGivenIndex(myDeck.getIndex_current_attack() + 1),
                myDeck.getAtk_deckAtGivenIndex(myDeck.getIndex_current_attack() + 2)};
        JOptionPane.showMessageDialog(null, "The Top Three Cards of My Deck Is: " + first3Cards[0] + ", " +
                first3Cards[1] + ", " + first3Cards[2]);
    }


}
