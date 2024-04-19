package truco;

public class Card {
    private String card;
    private String naipe;
    private int value;

    public Card(String card, String naipe, int value) {
        this.card = card;
        this.naipe = naipe;
        this.value = value;
    }

    public void showCard() {
        System.out.println(this.card + " de " + this.naipe);
    }

    public int cardValue() {
        return this.value;
    }

    public String cardNumber() {
        return this.card;
    }

    public String cardNaipe() {
        return this.naipe;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
