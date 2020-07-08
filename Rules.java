package Project;

import javax.swing.*;
import java.util.*;


public class Rules
{

    public static int score(HumanPlayer human,Dealer dealer)
    {
        Card [] playerCards;
        Card [] dealerCards;

       playerCards = human.getCards();
       dealerCards = dealer.getCards();

       if(checkForRoyalFlush(playerCards,dealerCards))
           return 10;
       else if(checkForStraigthFlush(playerCards,dealerCards))
           return 9;
        else if(checkForPairs(playerCards, dealerCards).equals("Four of a Kind"))
            return 8;
        else if(checkForTwoandThreePair(playerCards,dealerCards).equals("Full House"))
            return 7;
       else if(checkForFlush(playerCards,dealerCards))
            return 6;
       else if(checkForStraight(playerCards,dealerCards))
            return 5;
        else if(checkForPairs(playerCards, dealerCards).equals("Three of a Kind"))
            return 4;
        else if(checkForTwoandThreePair(playerCards,dealerCards).equals("Two Pair"))
            return 3;
       else if(checkForPairs(playerCards, dealerCards).equals("Pair"))
            return 2;
       else
           return 1;






    }

    public static  int  compareHighCards(Card [] c, Card [] p)
    {
        if(c[0].getFace().ordinal() + c[1].getFace().ordinal() > p[0].getFace().ordinal() + p[1].getFace().ordinal())
            return 1;
        if(c[0].getFace().ordinal() + c[1].getFace().ordinal() < p[0].getFace().ordinal() + p[1].getFace().ordinal())
            return 2;
        else
            return 0;



    }
    private static boolean checkForRoyalFlush(Card [] p,Card [] d)
    {
        HashMap<Card.Suit, ArrayList<Integer>> map = new HashMap<>();

        // put, get, containsKey
        for(Card f : p) {
            Card.Suit key = f.getSuit();
            if (!map.containsKey(key)) {
                ArrayList arr = new ArrayList();
                arr.add(f.getFace().ordinal());

                map.put(key, arr);
            } else {
               ArrayList arr = map.get(key);
               arr.add(f.getFace().ordinal());
                map.put(key, arr);
            }
        }
        for(Card f : d) {
            Card.Suit key = f.getSuit();
            if (!map.containsKey(key)) {
                ArrayList arr = new ArrayList();
                arr.add(f.getFace().ordinal());

                map.put(key, arr);
            } else {
                ArrayList arr = map.get(key);
                arr.add(f.getFace().ordinal());
                map.put(key, arr);
            }
        }


    ArrayList heartArr =   map.get(Card.Suit.Hearts);
   if(checkArrayList(heartArr))
       return true;



        ArrayList spadeArr =   map.get(Card.Suit.Spades);
        if(checkArrayList(spadeArr))
            return true;


        ArrayList diamondArr =   map.get(Card.Suit.Diamonds);
        if(checkArrayList(diamondArr))
            return true;



        ArrayList clubArr =   map.get(Card.Suit.Clubs);
        if(checkArrayList(clubArr))
            return true;



        return false;
    }
    private static boolean checkArrayList(ArrayList arr)
    {
        if(arr != null)
        {
            Collections.sort(arr);
            int c = 0;
            int l = (int) arr.get(0);



            for(int i = 1; i < arr.size(); i++)
            {
                int x = (int) arr.get(i);
                if(x < 8)
                    continue;
                if(x - 1 == l)
                {
                    c++;
                    l = x;

                    if(c == 4)
                        break;


                }
                else if(x == l)
                    continue;
                else
                {
                    c = 0;
                    l = x;


                }
            }
            if(c == 4)
                return true;


        }
        return false;
    }
    private static boolean checkArrayListStraightFlush(ArrayList list)
    {
        if(list != null)
        {
            Collections.sort(list);
            int c = 0;
            int l = (int) list.get(0);



            for(int i = 1; i < list.size(); i++)
            {
                int x = (int) list.get(i);

                if(x - 1 == l)
                {
                    c++;
                    l = x;

                    if(c == 4)
                        break;


                }
                else if(x == l)
                    continue;
                else
                {
                    c = 0;
                    l = x;


                }
            }
            if(c == 4)
                return true;


        }
        return false;


    }
    private static String checkForTwoandThreePair(Card [] p, Card [] d)
    {
        HashMap<Card.Face, Integer> map = new HashMap<>();

        // put, get, containsKey
        for(Card f : p) {
            Card.Face key = f.getFace();
            if (!map.containsKey(key)) {
                map.put(key, 1);
            } else {
                map.put(key, map.get(key) + 1);
            }
        }
        for(Card f : d) {
            Card.Face key = f.getFace();
            if (!map.containsKey(key)) {
                map.put(key, 1);
            } else {
                map.put(key, map.get(key) + 1);
            }
        }
        boolean threeOfaKind = false, twoOfaKind = false;
        int pairCounter = 0;

        for (int i=Card.Face.Ace.ordinal(); i>0; i--) {
            Card.Face cardFace = Card.Face.values()[i];
            Integer numOfCards = map.get(cardFace);
            if (numOfCards != null) {

                if(numOfCards == 3)
                    threeOfaKind = true;
                if (numOfCards == 2)
                {
                    twoOfaKind = true;
                    pairCounter++;

                }


                //  return "number of " + cardFace + ": " + map.get(cardFace);
            }

        }
        if(threeOfaKind && twoOfaKind)
            return "Full House";
        if(pairCounter == 2)
            return "Two Pair";

        return "";

    }

    private static boolean checkForStraigthFlush(Card [] p,Card [] d)
    {
        HashMap<Card.Suit, ArrayList<Integer>> map = new HashMap<>();

        // put, get, containsKey
        for(Card f : p) {
            Card.Suit key = f.getSuit();
            if (!map.containsKey(key)) {
                ArrayList arr = new ArrayList();
                arr.add(f.getFace().ordinal());

                map.put(key, arr);
            } else {
                ArrayList arr = map.get(key);
                arr.add(f.getFace().ordinal());
                map.put(key, arr);
            }
        }
        for(Card f : d) {
            Card.Suit key = f.getSuit();
            if (!map.containsKey(key)) {
                ArrayList arr = new ArrayList();
                arr.add(f.getFace().ordinal());

                map.put(key, arr);
            } else {
                ArrayList arr = map.get(key);
                arr.add(f.getFace().ordinal());
                map.put(key, arr);
            }
        }


        ArrayList heartArr =   map.get(Card.Suit.Hearts);
        if(checkArrayListStraightFlush(heartArr))
            return true;



        ArrayList spadeArr =   map.get(Card.Suit.Spades);
        if(checkArrayListStraightFlush(spadeArr))
            return true;


        ArrayList diamondArr =   map.get(Card.Suit.Diamonds);
        if(checkArrayListStraightFlush(diamondArr))
            return true;



        ArrayList clubArr =   map.get(Card.Suit.Clubs);
        if(checkArrayListStraightFlush(clubArr))
            return true;



        return false;
    }

    private static boolean checkForStraight(Card [] p,Card [] d)
    {
        Card [] c = new Card[p.length + d.length];

        for(int i = 0; i < p.length; i++)
        c[i] = p[i];

        for(int j = p.length, k = 0; k < d.length; j++,k++)
            c[j] = d[k];

      c =  sortByNumber(c);

        int counter = 0;
        int lowest = c[0].getFace().ordinal();



        for(int i = 1; i < c.length; i++)
      {
          if(c[i].getFace().ordinal() - 1 == lowest)
          {
              counter++;
              lowest = c[i].getFace().ordinal();

              if(counter == 4)
                  break;


          }
          else if(c[i].getFace().ordinal() == lowest)
              continue;
          else
          {
              counter = 0;
              lowest = c[i].getFace().ordinal();


          }
      }

if(counter == 4)
    return true;

        return false;

    }
    private static Card[] sortByNumber(Card [] p)
    {
        Comparator<Card> size = Comparator.comparing(Card::getFace);
        Arrays.sort(p,size);

        return p;


    }
    private static boolean checkForFlush(Card [] p,Card [] d)
    {
        int clubs = 0, diamonds = 0, spades = 0, hearts = 0;
        int humanClubs = 0, humanDiamonds = 0, humanSpades = 0, humanHearts = 0;
        for(Card f : p)
        {
            if(f.getSuit() == Card.Suit.Clubs)
            humanClubs++;
            else if(f.getSuit() == Card.Suit.Diamonds)
            humanDiamonds++;
            else if(f.getSuit() == Card.Suit.Spades)
            humanSpades++;
            else
            humanHearts++;
        }
        for(Card c : d)
        {
            if(c.getSuit() == Card.Suit.Clubs)
                clubs++;
            else if(c.getSuit() == Card.Suit.Diamonds)
                diamonds++;
            else if(c.getSuit() == Card.Suit.Spades)
                spades++;
            else
                hearts++;

        }
        if(humanClubs + clubs >= 5)
            return true;
        else if(humanDiamonds + diamonds >= 5)
            return true;
        else if(humanSpades + spades >= 5)
            return true;
        else if(humanHearts + hearts >= 5)
            return true;
        else
            return false;


    }
    private static String checkForPairs(Card [] p,Card [] d)
    {
        HashMap<Card.Face, Integer> map = new HashMap<>();

        // put, get, containsKey
        for(Card f : p) {
            Card.Face key = f.getFace();
            if (!map.containsKey(key)) {
                map.put(key, 1);
            } else {
                map.put(key, map.get(key) + 1);
            }
        }
        for(Card f : d) {
            Card.Face key = f.getFace();
            if (!map.containsKey(key)) {
                map.put(key, 1);
            } else {
                map.put(key, map.get(key) + 1);
            }
        }
        boolean fourKind = false, threeKind = false, pair = false;
        for (int i=Card.Face.Ace.ordinal(); i>0; i--) {
            Card.Face cardFace = Card.Face.values()[i];
            Integer numOfCards = map.get(cardFace);
            if (numOfCards != null) {
                if(numOfCards == 4)
                    fourKind = true;
                if(numOfCards == 3)
                    threeKind = true;
                if (numOfCards == 2)
                    pair = true;

            }
        }
        if(fourKind)
            return "Four of a Kind";
        if(threeKind)
            return "Three of a Kind";
        if (pair)
            return "Pair";

        return "null";

    }


}
