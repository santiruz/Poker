package Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


class Game
{

    public static int pot = 0;
    public static int blind = 5;
    public static int index = -1;
    private static int counter = 0;
    public static int highestScore = 0;
    private static boolean skip = false, draw = false;
    public static int numPlayers = 3;
    private static boolean endGame = false;
    public static int folded = 0;
    public static int round = 1;


    private static int currentPlayerTurn = 0;
    private static HumanPlayer[] players;
    private static Dealer dealer;
    private static DeckOfCards deck;


    public static void initialize() {
//                System.out.println("Enter the number of Players");
//        isValidNumber();

        players = new HumanPlayer[numPlayers];
        deck = new DeckOfCards();
        dealer = new Dealer(deck);
        for(int j = 0; j < numPlayers; j++)
        {
            players[j] = new HumanPlayer(deck,j+1);
        }
    }
    public static void getNewDeck()
    {
        deck = new DeckOfCards();

        for(int i = 0; i < players.length; i++)
            players[i].setCards(deck);

        dealer = new Dealer(deck);
    }

    public static Dealer getDealer()
    {
        return dealer;
    }

    public static void setCurrentPlayerTurn()
    {

        counter++;

        if(players[currentPlayerTurn].isRaised()) {
            counter = 1;
        }


        if(counter == numPlayers) {
            round++;
            counter = 0;

        }

            if(currentPlayerTurn == numPlayers - 1)
                currentPlayerTurn = 0;
            else
                currentPlayerTurn++;

        }


    public static DeckOfCards getDeck()
    {
        return deck;
    }

    public static boolean allFold()
    {
        int counter = 0;
        for(int i =0; i < players.length;i++)
        {
            if(players[i].isFold())
                counter++;
        }
        if(counter == numPlayers - 1)
            return true;
        else
            return false;
    }
    public static boolean allPlayersAllIn()
    {
        int counter = 0;
        for(int i =0; i < players.length;i++)
        {
            if(players[i].isAllIn())
                counter++;
        }
        if(counter == numPlayers)
            return true;
        else
            return false;
    }
    public static boolean allPlayersButOneAllIn()
    {
        int counter = 0;
        for(int i =0; i < players.length;i++)
        {
            if(players[i].isAllIn())
                counter++;
        }
        if(counter == numPlayers - 1)
            return true;
        else
            return false;
    }

    public static void setPot(int pot)
    {
        Game.pot = pot;
    }

    public static int getNumPlayers()
    {
        return numPlayers;
    }

    public static void unsetRaise()
    {
        for(int i =0; i < players.length;i++)
            players[i].unsetRaise();
    }
    public static void getBlind()
    {
        JOptionPane.showMessageDialog(null,"Now Taking the Blind from each player, which starts at $5");
        for(int i =0; i < numPlayers; i++)
        {
            players[i].setCurrency(players[i].getCurrency()-blind);
            if(players[i].getCurrency() == 0)
                players[i].setAllIn();

            pot += blind;
        }
    }


    public static int getFolded()
    {
        return folded;
    }

    public static HumanPlayer[] getPlayers()
    {
        return players;
    }

    public static HumanPlayer getCurrentPlayer() {
        return players[currentPlayerTurn];
    }
    public static void unsetCalls()
    {
        for(int i =0; i < players.length;i++)
        {
            players[i].unsetCall();
        }
    }



    public static boolean IfAllCalled()
    {
        int counter = 0;
        for(int l = 0; l < numPlayers; l++)
        {
            if(players[l].isCall())
                counter++;
        }
        if(counter + folded == numPlayers)
        {

            for (int f = 0; f < numPlayers; f++)
                players[f].unsetCall();
            return true;


        }
        return false;

    }

    private static String getWinningName(int s)
    {
      if(s == 10)
        return "Royal Flush";
      else if(s == 9)
      return "Straight Flush";
      else if(s == 8)
        return "Four of a Kind";
      else if(s == 7)
        return "Full House";
      else if(s == 6)
        return "Flush";
      else if(s == 5)
        return "Straight";
      else if(s == 4)
        return "Three of a Kind";
      else if(s == 3)
        return "Two Pair";
      else if(s == 2)
        return "Pair";
      else
        return "High Card";

    }
    public static void getHighestScore(HumanPlayer [] players, Dealer dealerHand,int num,int fold)
    {
        int playerNumberWinner = 0;
        boolean skip = false;
        for(int i = 0; i < players.length; i++)
        {
            players[i].setScore(Rules.score(players[i],dealerHand));
        }



        for(int j = 0; j < players.length;j++)
        {
            if(players[j].isFold())
                continue;

            if(num - fold == 1)
            {
                for(int i =0; i < players.length;i++)
                {
                    if(!players[j].isFold())
                    {
                        playerNumberWinner = j;
                        highestScore = players[j].getScore();
                        skip = true;
                        break;
                    }

                }
            }
            if(skip)
                break;

            for(int k = 1; k < players.length; k++)
            {
                if(players[k].isFold())
                    continue;

                if(players[k].getScore() == players[j].getScore() && players[k].getPlayerNumber() != players[j].getPlayerNumber() && players[k].getScore() >= highestScore)
                {

                    int playerScore = Rules.compareHighCards(players[j].getCards(),players[k].getCards());
                    if(playerScore == 1)
                        highestScore = players[j].getScore();
                    else if(playerScore == 2)
                        highestScore = players[k].getScore();
                    else
                        draw = true;

                }

                if(players[k].getScore() > players[j].getScore())
                {
                        highestScore = players[k].getScore();
                        playerNumberWinner = k;



                }
                else if(players[k].getScore() < players[j].getScore())
                {
                    highestScore = players[j].getScore();
                    playerNumberWinner = j;
                }


            }

        }
    if(draw)
    {
        JOptionPane.showMessageDialog(null,"Draw");
        return;
    }

        for(int j = 0; j < players.length; j++)
        {
            players[j].fold();
        }

        players[playerNumberWinner].unSetFold();


        for(int i = 0; i < players.length; i++)
        {
            if(!players[i].isFold())
            {
                //card 1
                Card[] cards = players[i].getCards();
                ImageIcon card1 = cards[0].getImage();

                // card 2
                ImageIcon card2 = cards[1].getImage();

                players[i].setCurrency(pot + players[i].getCurrency());
                JOptionPane.showMessageDialog(null,
                       new JLabel("Player " + (playerNumberWinner + 1) + " wins the pot of $" + pot + " with a " + getWinningName(highestScore),card1,JLabel.LEFT), "Winning Hand",JOptionPane.INFORMATION_MESSAGE,card2);

            }
        }

        sortByCurrency(players);
        for(int k = 0; k < players.length;k++)
        {
            if(players[k].getCurrency() <= 0)
            {
                players = removeTheElement(players,k);
                k--;
            }


        }
        if(players.length == 1)
        {

            JOptionPane.showMessageDialog(null,"Player " + players[0].getPlayerNumber() + " is the final winner");
            System.exit(0);
        }
    }
    private static HumanPlayer[] sortByCurrency(HumanPlayer [] p)
    {
        Comparator<HumanPlayer> size = Comparator.comparing(HumanPlayer::getCurrency);

        Comparator<HumanPlayer> reversedSize = size.reversed();

        Arrays.sort(p,reversedSize);

        return p;


    }
    public static void unSetAll()
    {
        for(int i =0; i < players.length; i++)
        {
            players[i].unSetFold();
            players[i].unsetCall();
            players[i].unsetRaise();
            players[i].unsetScore();
            players[i].unsetAllIn();


        }
        pot = 0;
        folded = 0;
        round = 1;


    }


    private static HumanPlayer [] removeTheElement(HumanPlayer [] arr,
                                         int index)
    {

        // If the array is empty
        // or the index is not in array range
        // return the original array
        if (arr == null
                || index < 0
                || index >= arr.length) {

            return arr;
        }

        // Create another array of size one less
        HumanPlayer [] anotherArray = new HumanPlayer[arr.length - 1];

        // Copy the elements from starting till index
        // from original array to the other array
        System.arraycopy(arr, 0, anotherArray, 0, index);

        // Copy the elements from index + 1 till end
        // from original array to the other array
        System.arraycopy(arr, index + 1,
                anotherArray, index,
                arr.length - index - 1);

        // return the resultant array
        return anotherArray;
    }

}




