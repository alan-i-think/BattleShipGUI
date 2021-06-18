import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App implements ActionListener
{
    Game game = new Game(10);

    JFrame frame = new JFrame();
    
    JPanel gamePanel = new JPanel();
    JButton[] buttons = new JButton[100];
    
    JPanel endPanel = new JPanel();
    JLabel scoreLabel = new JLabel();
    JButton resetButton = new JButton();

    public App()
    {
        // Create frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500 + 16, 500 + 38);
        frame.setResizable(false);
        frame.setTitle("Battleship");

        frame.add(gamePanel);
        frame.add(endPanel);


        // GAME SCREEN //
        gamePanel.setLayout(new GridLayout(10, 10));
        gamePanel.setBounds(0, 0, 500, 500);
        gamePanel.setVisible(true);

        for (int i = 0; i < game.getWidth() * game.getWidth(); i++)
        {
            buttons[i] = new JButton();
            gamePanel.add(buttons[i]);

            buttons[i].setFont(new Font("Arial", Font.BOLD, 16));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }


        // END SCREEN //

        endPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        endPanel.setVisible(false);

        endPanel.add(scoreLabel);
        endPanel.add(resetButton);

        scoreLabel.setText("0% Accuracy");
        scoreLabel.setForeground(new Color(25, 25, 25));
        scoreLabel.setPreferredSize(new Dimension(500, 100));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setVerticalAlignment(JLabel.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        scoreLabel.setOpaque(true);

        resetButton.setText("Reset");
        resetButton.setFont(new Font("SansSerif", Font.PLAIN, 32));
        resetButton.setPreferredSize(new Dimension(150, 60));
        resetButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        resetButton.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);
    }

    public void Start()
    {
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        // Check for reset button
        if (event.getSource() == resetButton)
        {
            ResetGame();
        }

        // Check for grid buttons
        for (int i = 0; i < game.getWidth(); i++)
        {
            for (int j = 0; j < game.getWidth(); j++)
            {
                if (event.getSource() == buttons[i + 10*j])
                {
                    int guess = game.guessPos(i, j);

                    if (guess == 0)
                    {
                        // Miss
                        buttons[i + 10*j].setText("/");
                    }
                    else if (guess == 1)
                    {
                        // Hit
                        buttons[i + 10*j].setText("X");
                    }

                    // Check game state
                    if (!game.getState())
                    {
                        EndGame();
                    }
                }
            }
        }
    }

    private void EndGame()
    {
        int score = (int) (game.getScore() * 100);
        scoreLabel.setText(score + "% Accuracy");

        gamePanel.setVisible(false);
        endPanel.setVisible(true);
    }

    private void ResetGame()
    {
        game.reset();

        // Clear grid buttons
        for (int i = 0; i < game.getWidth() * game.getWidth(); i++)
        {
            buttons[i].setText("");
        }

        endPanel.setVisible(false);
        gamePanel.setVisible(true);
    }
}