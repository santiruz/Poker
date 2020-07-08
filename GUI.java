package Project;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class GUI extends JFrame
{
    private static JFrame mainFrame;
    private static JLayeredPane tablePanel;
    private static JPanel buttonPanel;
    private static JButton playGame;
    private static JButton raise;
    private static JButton Fold;
    private static JButton Check,Call;
    private static Game game;
    private static Card [] dealerCards;
    private static int counter = 0, num = 0;
    private static int bet = 0;
    public static boolean skip = false, allfold = false;


    private static JLayeredPane cardPanel = new JLayeredPane();

    private static JLabel potLabel = new JLabel("<Pot Label>");
    private static JLabel betLabel = new JLabel("<Bet Label>");
    private static JLabel balanceLabel = new JLabel("Balance: $0.00");
    private static JLabel playerLabel = new JLabel("<Player Label>");


    public GUI(Game g)
    {
        this.game = g;
        mainFrame = new JFrame("Poker");
        mainFrame.getContentPane().setBackground(Color.GREEN.darker().darker());
        mainFrame.setLayout(new FlowLayout());
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1200, 800);
        mainFrame.setResizable(false);


        playGame = new JButton("Play Game");


        mainFrame.add(playGame);

        playGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                startGame();
            }
        });


        mainFrame.setVisible(true);


    }

    public static void main(String[] args)
    {
        Game g = new Game();
        Game.initialize();
        new GUI(g);
    }

    private static Rectangle rect(int x, int y, Dimension size)
    {
        Rectangle result = new Rectangle(new Point(x, y), size);
        return result;
    }

    private void startGame()
    {


        playGame.setVisible(false);


        dealerCards = Game.getDealer().getCards();



        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GREEN.darker().darker());
        raisePanel();

        Check = new JButton("Check");
        Check.setPreferredSize(new Dimension(150, 100));
        Check.setBackground(Color.BLACK);
        Check.setForeground(Color.WHITE);
        Check.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                Game.setCurrentPlayerTurn();
                rerender();
            }
        });
        buttonPanel.add(Check);

        Fold = new JButton("Fold");
        Fold.setPreferredSize(new Dimension(150, 100));
        Fold.setBackground(Color.BLACK);
        Fold.setForeground(Color.WHITE);
        Fold.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Game.getCurrentPlayer().fold();
                Game.setCurrentPlayerTurn();
                rerender();
            }
        });
        buttonPanel.add(Fold);


        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setVisible(true);


        ImageIcon t = new ImageIcon("src\\Project\\assets\\table1.png");
        JLabel table = new JLabel(t);
        tablePanel = new JLayeredPane();
        table.setBounds(rect(mainFrame.getWidth()/2 - mainFrame.getWidth()/4, 10, table.getPreferredSize()));
        tablePanel.add(table, 4);
        mainFrame.add(tablePanel);

        this.rerender();

    }

    public void rerender()
    {
        if(Game.round == 1 && !skip)
        {
            Game.getBlind();
            skip = true;
        }


        // if fold skip player
        if(Game.getCurrentPlayer().isFold())
            Game.setCurrentPlayerTurn();

        if(Game.getCurrentPlayer().isAllIn())
            Game.setCurrentPlayerTurn();

        if(Game.allFold()) {
            Game.round = 5;
            allfold = true;
        }


        if(Game.IfAllCalled())
        {
            Call.setVisible(false);
            Check.setVisible(true);
            bet = 0;
            counter = 0;
            Game.unsetCalls();
            Game.unsetRaise();

        }

        if(Game.allPlayersAllIn())
            Game.round = 5;

        if(Game.round == 5) {
            river();
            Game.getHighestScore(Game.getPlayers(), Game.getDealer(), Game.getNumPlayers(), Game.getFolded());
            Game.unSetAll();
           Game.getNewDeck();
           dealerCards = Game.getDealer().getCards();
           cardPanel.removeAll();
           cardPanel.setVisible(false);
           skip = false;
           num = 0;
           if(allfold)
               Game.setCurrentPlayerTurn();
           allfold = false;
            rerender();
        }

       // animate(potLabel,new Point(400,500),5,7000);


        //pot
        potLabel.setText("Pot: $" + Game.pot);
        potLabel.setFont(new Font("Serif", Font.PLAIN, 23));
        potLabel.setBounds(rect(10, 45, new Dimension(150, 100)));
        tablePanel.add(potLabel, 0);

        //bet
        betLabel.setText("Bet: $" + bet);
        betLabel.setFont(new Font("Serif", Font.PLAIN, 23));
        betLabel.setBounds(rect(10, 85, new Dimension(150, 95)));
        tablePanel.add(betLabel, 0);


        // balance
        balanceLabel.setText("Balance: $" + Game.getCurrentPlayer().getCurrency());
        balanceLabel.setFont(new Font("Serif", Font.PLAIN, 23));
        balanceLabel.setBounds(rect(10, 10, new Dimension(150, 100)));
        tablePanel.add(balanceLabel, 0);

        // player
        HumanPlayer currentPlayer = Game.getCurrentPlayer();
        playerLabel.setText("Player " + currentPlayer.getPlayerNumber());
        playerLabel.setFont(new Font("Serif", Font.PLAIN, 23));
        playerLabel.setBounds(rect(10, 0, new Dimension(150, 50)));
        tablePanel.add(playerLabel, 0);

        // card 1
        Card[] cards = currentPlayer.getCards();
        ImageIcon card1 = cards[0].getImage();
        JLabel cardLabel = new JLabel(card1);
        cardLabel.setBounds(rect(625, 500, cardLabel.getPreferredSize()));
        tablePanel.add(cardLabel, 0);

        // card 2
        ImageIcon card2 = cards[1].getImage();
        JLabel card2Label = new JLabel(card2);
        card2Label.setBounds(rect(525, 500, card2Label.getPreferredSize()));
        tablePanel.add(card2Label, 0);

        // Dealer Card Handler


         if(Game.round == 2) {
             flop();

         }
        else if(Game.round == 3) {
             turn();

         }
        else if(Game.round == 4) {
             river();
         }



    }
    private final void flop()
    {

        int x = 400;
        for(int i =0; i < dealerCards.length - 2;i++)
        {
            ImageIcon c = dealerCards[i].getImage();
            JLabel cardLabels = new JLabel(c);
            cardLabels.setBounds(rect(x,125, cardLabels.getPreferredSize()));
            x += 75;
            cardPanel.add(cardLabels,0);

        }

        mainFrame.add(cardPanel,0);
        cardPanel.setVisible(true);
    }
    private final void turn()
    {


        int x = 400;
        for (int i = 0; i < dealerCards.length - 1; i++) {
            ImageIcon c = dealerCards[i].getImage();
            JLabel cardLabels = new JLabel(c);
            cardLabels.setBounds(rect(x, 125, cardLabels.getPreferredSize()));
            x += 75;
            cardPanel.add(cardLabels, 0);


        }
        mainFrame.add(cardPanel,0);
    }
        private final void river()
        {


            int x = 400;
            for(int i =0; i < dealerCards.length;i++)
            {
                ImageIcon c = dealerCards[i].getImage();
                JLabel cardLabels = new JLabel(c);
                cardLabels.setBounds(rect(x,125, cardLabels.getPreferredSize()));
                x+=75;
                cardPanel.add(cardLabels,1);


            }
            mainFrame.add(cardPanel,0);
            cardPanel.setVisible(true);

        }
        private void callPanel()
        {
            Call = new JButton("Call");
            Call.setPreferredSize(new Dimension(150, 100));
            Call.setBackground(Color.BLACK);
            Call.setForeground(Color.WHITE);
            Call.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Game.getCurrentPlayer().setCurrency(Game.getCurrentPlayer().getCurrency() - bet);

                    if(Game.getCurrentPlayer().getCurrency() <= 0) {
                        Game.getCurrentPlayer().setAllIn();
                        Game.getCurrentPlayer().setCurrency(0);
                    }
                    Game.setPot(Game.pot+bet);
                    Game.getCurrentPlayer().setCall();
                    Game.setCurrentPlayerTurn();
                    rerender();

                }
            });
            Check.setVisible(false);
            buttonPanel.add(Call,0);

        }
        private void raisePanel()
        {
            raise = new JButton("Raise");
            raise.setPreferredSize(new Dimension(150, 100));
            raise.setBackground(Color.BLACK);
            raise.setForeground(Color.WHITE);
            raise.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {



                    bet();

                    if(counter == 1)
                    callPanel();

                    rerender();
                }
            });
            buttonPanel.add(raise,0);

        }
    public void bet()
    {

        int b;
        int prevbet = bet;

        while(true)
        {
            b = Integer.parseInt(JOptionPane.showInputDialog(
                    "Enter a bet: " ));
            if((Game.getCurrentPlayer().getCurrency() - b) < 0
                    && prevbet >= bet
                    && b <= 0) {
                JOptionPane.showMessageDialog(null,"Invaild Bet");
                continue;
            }
            else{
                bet = Integer.parseInt(String.valueOf(b));
                break;

            }

        }



        Game.getCurrentPlayer().setCurrency(Game.getCurrentPlayer().getCurrency() - bet);
        Game.setPot(bet + Game.pot);
                Game.unsetCalls();
                Game.unsetRaise();
                Game.getCurrentPlayer().setRaised();
                Game.getCurrentPlayer().setCall();

                if(Game.getCurrentPlayer().getCurrency() <=0) {
                    Game.getCurrentPlayer().setAllIn();
                    Game.getCurrentPlayer().setCurrency(0);


                }


                counter++;

                Game.setCurrentPlayerTurn();







    }
