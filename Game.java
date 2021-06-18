import java.util.*;

public class Game
{
    private ArrayList<Ship> ships;
    private int[][] guesses;
    
    private int width;
    
    private boolean state;   
    private int hits;
    private int misses;

    Random rand; 
    
    public Game(int width)
    {
        this.width = width;

        this.ships = new ArrayList<Ship>();
        this.guesses = new int[width][width];

        this.rand = new Random();

        // Initialize board
        reset();
    }
    
    public void reset()
    {
        state = true;
        hits = 0;
        misses = 0;

        // Clear guesses
        for(int n = 0; n < width; n++)
        {
            for(int m = 0; m < width; m++)
            {
                guesses[n][m] = 0;
            }
        }

        // Remove ships
        ships.clear();

        // Place various sized ships
        placeShip(5);
        placeShip(4);
        placeShip(4);
        placeShip(3);
        placeShip(3);
    }
    
    public int guessPos(int x, int y)
    {
        if(guesses[x][y] == 1)
        {
            // You already guessed this!
            return 2;
        }

        guesses[x][y] = 1;

        for (Ship ship : ships)
        {
            if (ship.guess(x, y, true))
            {
                // Hit!
                hits++;

                if (ship.sunk())
                {
                    ships.remove(ship);
                }

                return 1;
            }
        }
        
        // Miss :(
        misses++;
        return 0;
    }

    public boolean getState()
    {
        if (hits >= 19) // 19 is total ship cells (5 + 4 + 4 + 3 + 3)
        {
            // Game ended
            state = false;
        }

        return state;
    }
    
    public double getScore()
    {
        return (double) (width*width - misses) / (double) (width*width); 
    }

    public int getWidth()
    {
        return width;
    }

    private void placeShip(int size)
    {
        int longPos = rand.nextInt(width - size + 1);
        int shortPos = rand.nextInt(width);
        
        int x;
        int y;

        boolean horizontal = rand.nextBoolean();

        if (horizontal)
        {
            // Horizontal
            x = longPos;
            y = shortPos;
        }
        else
        {
            // Vertical
            x = shortPos;
            y = longPos;
        }

        // Check if position is taken
        for (Ship ship : ships)
        {
            for (int i = 0; i < size; i++)
            {
                if (horizontal && ship.guess(x + i, y, false) || !horizontal && ship.guess(x, y + i, false))
                {
                    // Try again
                    placeShip(size);
                    return;
                }
            }
        }

        // Place ship!
        Ship newShip = new Ship();
        ships.add(newShip);

        for (int i = 0; i < size; i++)
        {
            if (horizontal)
            {
                newShip.add(x + i, y);
            }
            else
            {
                newShip.add(x, y + i);
            }
        }
        
        return;
    }
}
