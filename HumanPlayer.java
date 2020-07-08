package Project;

import java.util.Scanner;

public class HumanPlayer
{
    Scanner input = new Scanner(System.in);
    private Card [] cards;
    private DeckOfCards deck;
    private int currency;
    private boolean fold = false;
    private boolean call = false;
    private boolean raised = false;
    private boolean allIn = false;
    private int playerNumber;
    private int score;




    HumanPlayer(DeckOfCards d,int p) // DeckofCards d
    {

        deck = d;
        cards = new Card[2];
        currency = 100;
        playerNumber = p;



        for(int x = 0; x < 2; x++)
            cards[x] = deck.drawFromDeck();
    }


    public void setAllIn()
    {
        allIn = true;
    }
    public void setRaised()
    {
        raised = true;
    }

    public int getScore()
    {
        return score;
    }

    public void setCurrency(int currency)
    {
        this.currency = currency;
    }
    public void setScore(int s)
    {
        this.score = s;
    }

    public int getCurrency()
    {
        return currency;
    }


    public Card[] getCards()
    {
        return cards;
    }
    public void setCards(DeckOfCards d)
    {
        cards = new Card[2];
        for(int i =0; i < cards.length;i++)
            cards[i] = d.drawFromDeck();
    }

    public boolean isFold()
    {
        return fold;
    }

    public void fold()
    {
        Game.folded++;
        fold = true;
    }

    public int getPlayerNumber()
    {
        return playerNumber;
    }

    public void unSetFold()
    {
        fold = false;
    }
    public void setCall()
    {
        call = true;
    }
    public void unsetCall()
    {
        call = false;
    }
    public void unsetScore()
    {
        score = 0;
    }
    public void unsetAllIn()
    {
        allIn = false;
    }
    public void unsetRaise()
    {
        raised = false;
    }
    public boolean isRaised()
    {
        return raised;
    }
    public boolean isAllIn()
    {
        return allIn;
    }

    public boolean isCall()
    {
        return call;
    }


}
