package truco;

import java.util.ArrayList;

public class Cards {
    private ArrayList<Card> cards = new ArrayList<Card>();
    private ArrayList<String> numbers = new ArrayList<String>();
    private ArrayList<String> naipes = new ArrayList<String>();

    public Cards() {
        this.numbers.add("4");
        this.numbers.add("5");
        this.numbers.add("6");
        this.numbers.add("7");
        this.numbers.add("Q");
        this.numbers.add("J");
        this.numbers.add("K");
        this.numbers.add("A");
        this.numbers.add("2");
        this.numbers.add("3");

        this.naipes.add("ouros");
        this.naipes.add("espadas");
        this.naipes.add("copas");
        this.naipes.add("paus");

        int count = 0;
        Card prevCard = null;

        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < naipes.size(); j++) {
                String number = this.numbers.get(i);
                String naipe = this.naipes.get(j);

                if (prevCard != null) {
                    if (prevCard.cardNumber() != number) {
                        count ++;
                    }
                } else {
                    count++;
                }

                Card card = new Card(number, naipe, count);
                this.cards.add(card);

                prevCard = card;
            }
        }
    };

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public void showCards() {
        for (int i = 0; i < 40; i++) {
            Card card = this.cards.get(i);
            card.showCard();
            int value = card.cardValue();
            System.out.println(value);
        }
    }
}
