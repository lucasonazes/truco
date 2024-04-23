package truco;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private int score1;
    private int score2;
    private int tempScore;
    private int tempScore2;
    private Card vira;
    private Cards cards;
    private Player player1;
    private Player player2;
    private Player prevPlayer;
    private int trucado;
    private boolean accept;
    private Scanner scanner = new Scanner(System.in);

    public Game() {
        this.cards = new Cards();
        // this.cards.showCards();

        for (;;) {
            this.score1 = 0;
            this.score2 = 0;
            this.prevPlayer = null;
            header();

            System.out.println("Qual é o nome do jogador 1?");
            String name = scanner.nextLine();
            this.player1 = new Player(name);
            System.out.println("Qual é o nome do jogador 2?");
            name = scanner.nextLine();
            this.player2 = new Player(name);
            System.out.println("\n");

            System.out.println("Até quantos pontos vocês querem jogar? ");
            int points = scanner.nextInt();
            System.out.println("\n");

            clrScreen();

            for (;;) {
                round();
                if (score1 >= points || score2 >= points)
                    break;
            }
            setWinner();

            boolean end = playAgain();
            clrScreen();
            if (!end)
                break;
        }
    }

    public void clrScreen() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public void header() {
        System.out.println("---------------------------");
        System.out.println("-----------TRUCO-----------");
        System.out.println("---------------------------");
        System.out.println("Pontuação: " + this.score1 + " X " + this.score2);
        System.out.println("\n");
    }

    public void round() {
        this.tempScore = 0;
        this.tempScore2 = 0;
        play();
    }

    public void play() {
        setHands();
        setVira();
        this.trucado = 0;
        this.accept = true;

        for (int i = 0; i < 3; i++) {
            clrScreen();
            header();
            showVira();
            showHands();
            Card choosedCard1 = null;
            Card choosedCard2 = null;
            boolean end;

            // If player1 started the last game
            if (this.prevPlayer == player1) {

                end = verifyTrucoPlayer2();
                if (end)
                    break;
                choosedCard2 = chooseCards(player2);

                end = verifyTrucoPlayer1();
                if (end)
                    break;
                choosedCard1 = chooseCards(player1);

                this.prevPlayer = player2;

                // The first game or if player2 started the last game
            } else {

                end = verifyTrucoPlayer1();
                if (end)
                    break;
                choosedCard1 = chooseCards(player1);

                end = verifyTrucoPlayer2();
                if (end)
                    break;
                choosedCard2 = chooseCards(player2);

                this.prevPlayer = player1;
            }
            System.out.println("\n");
            setPoints(choosedCard1, choosedCard2);
            System.out.println("Pressione enter para continuar...");
            scanner.nextLine();
            scanner.nextLine();
        }
        setMatchPoints();
    }

    public void setVira() {
        ArrayList<Card> shuffleCards = cards.getCards();
        // Generates a random card
        Collections.shuffle(shuffleCards);
        this.vira = shuffleCards.get(0);
    }

    public void setHands() {
        if (player1.handSize() > 0) {
            player1.clearHand();
        }
        if (player2.handSize() > 0) {
            player2.clearHand();
        }

        for (int i = 0; i < 3; i++) {
            ArrayList<Card> shuffleCards = cards.getCards();
            // Generates a random card
            Collections.shuffle(shuffleCards);
            Card shuffleCard = shuffleCards.get(0);
            Card shuffleCard2 = shuffleCards.get(1);
            player1.setHand(shuffleCard);
            player2.setHand(shuffleCard2);
        }
    }

    public void showVira() {
        System.out.println("O vira é: " + this.vira.cardNumber() + " de " + this.vira.cardNaipe());
        System.out.println("\n");
    }

    public void showHands() {
        player1.getHand();
        System.out.println("\n");
        player2.getHand();
        System.out.println("\n");
    }

    public Card chooseCards(Player player) {
        Card choosedCard;

        for (;;) {
            System.out.println(player.playerName() + ", escolha sua carta: ");
            int number = scanner.nextInt();
            System.out.println("\n");

            if (number > player.handSize()) {
                System.out.println("Carta inválida!\n");
            } else if (number == 1) {
                choosedCard = player.getCard(0);
                player.removeCard(0);
                System.out.println(player.playerName() + " jogou um " + choosedCard.cardNumber() + " de "
                        + choosedCard.cardNaipe() + "!\n");
                return choosedCard;
            } else if (number == 2) {
                choosedCard = player.getCard(1);
                player.removeCard(1);
                System.out.println(player.playerName() + " jogou um " + choosedCard.cardNumber() + " de "
                        + choosedCard.cardNaipe() + "!\n");
                return choosedCard;
            } else if (number == 3) {
                choosedCard = player.getCard(2);
                player.removeCard(2);
                System.out.println(player.playerName() + " jogou um " + choosedCard.cardNumber() + " de "
                        + choosedCard.cardNaipe() + "!\n");
                return choosedCard;
            } else {
                System.out.println("Carta inválida!\n");
            }
        }
    }

    public boolean verifyTrucoPlayer1() {
        if (trucado == 0) {
            trucar(player1);
            if (trucado != 0) {
                accept = acceptTruco(player2);
                if (!accept) {
                    tempScore = 5;
                    trucado = 0;
                    return true;
                }
                trucar(player2);
                if (trucado == 2) {
                    accept = acceptTruco(player1);
                    if (!accept) {
                        tempScore2 = 5;
                        trucado = 1;
                        return true;
                    }
                    trucar(player1);
                    if (trucado == 3) {
                        accept = acceptTruco(player2);
                        if (!accept) {
                            tempScore = 5;
                            trucado = 2;
                            return true;
                        }
                    }
                    trucar(player2);
                    if (trucado == 4) {
                        accept = acceptTruco(player1);
                        if (!accept) {
                            tempScore2 = 5;
                            trucado = 3;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean verifyTrucoPlayer2() {
        if (trucado == 0 && trucado < 4) {
            trucar(player2);
            if (trucado != 0) {
                accept = acceptTruco(player1);
                if (!accept) {
                    tempScore2 = 5;
                    trucado = 0;
                    return true;
                }
                trucar(player1);
                if (trucado == 2) {
                    accept = acceptTruco(player2);
                    if (!accept) {
                        tempScore = 5;
                        trucado = 1;
                        return true;
                    }
                    trucar(player2);
                    if (trucado == 3) {
                        accept = acceptTruco(player1);
                        if (!accept) {
                            tempScore2 = 5;
                            trucado = 2;
                            return true;
                        }
                    }
                    trucar(player1);
                    if (trucado == 4) {
                        accept = acceptTruco(player2);
                        if (!accept) {
                            tempScore = 5;
                            trucado = 3;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void trucar(Player player) {
        for (;;) {
            if (trucado == 1) {
                System.out.println(player.playerName() + ", você pede 6? (s/n): ");
            } else if (trucado == 2) {
                System.out.println(player.playerName() + ", você pede 9? (s/n): ");
            } else if (trucado == 3) {
                System.out.println(player.playerName() + ", você pede 12? (s/n): ");
            } else {
                System.out.println(player.playerName() + ", deseja trucar? (s/n): ");
            }
            scanner.nextLine();
            String response = scanner.nextLine();
            char charResponse = response.charAt(0);

            if (charResponse == 'n') {
                break;
            } else if (charResponse == 's') {
                this.trucado++;
                break;
            } else {
                System.out.println("A resposta deve ser 's' ou 'n'\n");
            }
        }
    }

    public boolean acceptTruco(Player player) {
        for (;;) {
            System.out.println(player.playerName() + ", você aceita? (s/n): ");
            String response = scanner.nextLine();
            char charResponse = response.charAt(0);

            if (charResponse == 'n') {
                return false;
            } else if (charResponse == 's') {
                return true;
            } else {
                System.out.println("A resposta deve ser 's' ou 'n'\n");
            }
        }
    }

    public int ifIsVira(Card card) {
        int difference = card.cardValue() - this.vira.cardValue();

        if (this.vira.cardNumber() == "3" && card.cardNumber() == "4") {
            difference = 1;
        }

        if (difference == 1 && card.cardNaipe() == "ouros") {
            return 1;
        } else if (difference == 1 && card.cardNaipe() == "espadas") {
            return 2;
        } else if (difference == 1 && card.cardNaipe() == "copas") {
            return 3;
        } else if (difference == 1 && card.cardNaipe() == "paus") {
            return 4;
        } else {
            return 0;
        }
    }

    public void setPoints(Card card1, Card card2) {
        int int1 = ifIsVira(card1);
        int int2 = ifIsVira(card2);

        if (int1 == 0 && int2 == 0) {
            if (card1.cardValue() > card2.cardValue()) {
                this.tempScore++;
                System.out.println(player1.playerName() + " venceu o turno!\n");
            } else if (card2.cardValue() > card1.cardValue()) {
                this.tempScore2++;
                System.out.println(player2.playerName() + " venceu o turno!\n");
            } else {
                System.out.println("O turno foi um empate!\n");
            }
        } else if (int1 > int2) {
            this.tempScore++;
            if (card1.cardNaipe() == "paus") {
                System.out.println(card1.cardNumber() + " de " + card1.cardNaipe() + " é o gato!");
            } else {
                System.out.println(card1.cardNumber() + " de " + card1.cardNaipe() + " é o vira!");
            }
            System.out.println(player1.playerName() + " venceu o turno!\n");
        } else if (int2 > int1) {
            this.tempScore2++;
            if (card2.cardNaipe() == "paus") {
                System.out.println(card2.cardNumber() + " de " + card2.cardNaipe() + " é o gato!");
            } else {
                System.out.println(card2.cardNumber() + " de " + card2.cardNaipe() + " é o vira!");
            }
            System.out.println(player2.playerName() + " venceu o turno!\n");
        } else {
            System.out.println("O turno foi um empate!\n");
        }
    }

    public void setMatchPoints() {
        if (this.trucado == 0) {
            if (this.tempScore > this.tempScore2) {
                this.score1++;
            } else if (this.tempScore2 > this.tempScore)
                this.score2++;
        } else {
            if (this.tempScore > this.tempScore2 && this.trucado == 1) {
                this.score1 += 3;
            } else if (this.tempScore2 > this.tempScore && this.trucado == 1) {
                this.score2 += 3;
            }
            if (this.tempScore > this.tempScore2 && this.trucado == 2) {
                this.score1 += 6;
            } else if (this.tempScore2 > this.tempScore && this.trucado == 2) {
                this.score2 += 6;
            }
            if (this.tempScore > this.tempScore2 && this.trucado == 3) {
                this.score1 += 9;
            } else if (this.tempScore2 > this.tempScore && this.trucado == 3) {
                this.score2 += 9;
            }
            if (this.tempScore > this.tempScore2 && this.trucado == 4) {
                this.score1 += 12;
            } else if (this.tempScore2 > this.tempScore && this.trucado == 4) {
                this.score2 += 12;
            }
        }
    }

    public boolean playAgain() {
        for (;;) {
            System.out.println("Deseja jogar novamente? (s/n): ");
            scanner.nextLine();
            String response = scanner.nextLine();
            char charResponse = response.charAt(0);

            if (charResponse == 'n') {
                return false;
            } else if (charResponse == 's') {
                return true;
            } else {
                System.out.println("A resposta deve ser 's' ou 'n'\n");
            }
        }
    }

    public void clearConsole() {
        System.out.println(System.lineSeparator().repeat(50));
    }

    public void setWinner() {
        if (score1 > score2) {
            System.out.println(player1.playerName() + " venceu a partida!");
        } else if (score2 > score1) {
            System.out.println(player2.playerName() + " venceu a partida!");
        } else {
            System.out.println("A partida foi um empate!");
        }
    }
}
