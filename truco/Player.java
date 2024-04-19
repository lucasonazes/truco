package truco;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand = new ArrayList<Card>();
    private boolean trucou = false;
    private boolean accept = true;

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

    public boolean trucou() {
        if(this.trucou == true) return true;
        return false;
    }

    public void setTrucou(boolean trucou) {
        if (trucou == true) {
            this.trucou = true;
        } else if (trucou == false) this.trucou = false;
    }

    public boolean accept() {
        if(this.accept == false) return false;
        return true;
    }

    public void setAccept(boolean accept) {
        if (accept == false) {
            this.accept = false;
        } else if (accept == true) this.accept = true;
    }


    public Integer handSize() {
        return this.hand.size();
    }
}
