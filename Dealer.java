package Project;

public class Dealer
{
    private Card [] cards;
    private DeckOfCards deck;


    public Dealer(DeckOfCards d) // DeckofCards d
    {
        deck = d;

        cards = new Card[5];

        for(int x = 0; x < 5; x++)
            cards[x] = deck.drawFromDeck();

    }

    public void setDealerCards(DeckOfCards d)
    {
        deck = d;

        cards = new Card[5];

        for(int i =0; i < cards.length;i++)
            cards[i] = deck.drawFromDeck();
    }

    public Card[] getCards()
    {
        return cards;
    }
}
