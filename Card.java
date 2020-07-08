package Project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

// class to represent a Card in a deck of cards
class Card {
    public enum Face {Two, Three, Four, Five, Six,
        Seven, Eight, Nine, Ten, Jack, Queen, King, Ace }
    public enum Suit {Clubs, Diamonds, Hearts, Spades}

    private final Face face;
    private final Suit suit;
    private final ImageIcon image;

    // constructor
    public Card(Face face, Suit suit) {
        this.face = face;
        this.suit = suit;
        this.image = new ImageIcon("src\\Project\\assets\\cards\\" + toString() + ".png");
    }


    // return face of the card
    public Face getFace() {return face;}

    // return suit of Card
    public Suit getSuit() {return suit;}

    // src\Project\assets\cards\Ace-Clubs.png
    public ImageIcon getImage()
    {
        return image;
    }

    // return String representation of Card
    public String toString() {
        return String.format("%s-%s", face, suit);
    }
}

// class DeckOfCards declaration
class DeckOfCards {
    public ArrayList<Card> list; // declare List that will store Cards
    private static int topCard = 0;

    // set up deck of Cards and shuffle
    public DeckOfCards() {
        Card[] deck = new Card[52];
        topCard = 0; // number of cards

        // populate deck with Card objects
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Face face : Card.Face.values()) {
                deck[topCard] = new Card(face, suit);
                ++topCard;
            }
        }

        list = new ArrayList<Card>(Arrays.asList(deck)); // get List

        Collections.shuffle(list);  // shuffle deck

    }

    // output deck

    public Card drawFromDeck()
    {
        Card c = list.remove(list.size() - 1);
        return c;
    }


    public static ImageIcon getCardFile(Card s, Card f) {
            return new ImageIcon(s + "-" + f + ".png");
    }

}
