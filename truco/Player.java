package truco;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand = new ArrayList<Card>();

    public Player(String name) {
        this.name = name;
    }

    public String playerName() {
        return this.name;
    }

    public void setHand(Card card) {
        this.hand.add(card);
    }

    public void getHand() {
        System.out.println(this.name + ", suas cartas são: ");
        for (int i = 0; i < this.hand.size(); i++) {
            Card card = this.hand.get(i);
            int j = i + 1;
            System.out.println(j + " - " + card.cardNumber() + " de " + card.cardNaipe());
        }
    }

    public Card getCard(int index) {
        return this.hand.get(index);
    }

    public void removeCard(int index) {
        this.hand.remove(index);
    }

    public Integer handSize() {
        return this.hand.size();
    }

    public void clearHand() {
        this.hand.clear();
    }
}
