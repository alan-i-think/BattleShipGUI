import java.util.*;

public class Game
{
    private int[][] cells;
    private int[][] guesses;
    
    private int rows;
    private int cols;
    private int shipSize;
    
    private boolean state;   
    private int hits;
    private int misses;

    Random rand; 
    
    public Game(int rows, int columns, int shipSize)
    {
        this.rows = rows;
        this.cols = columns;
        this.shipSize = shipSize;

        this.cells = new int[rows][columns];
        this.guesses = new int[rows][columns];

        this.rand = new Random();

        // Initialize board
        Reset();
    }
    
    public void Reset()
    {
        state = true;
        hits = 0;
        misses = 0;

        // Clear board
        for(int n = 0; n < rows; n++)
        {
            for(int m = 0; m < cols; m++)
            {
                cells[n][m] = 0;
                guesses[n][m] = 0;
            }
        }

        // Get ship position
        int pos = rand.nextInt(cols - shipSize + 1);
        
        // Place ship
        for (int i = pos; i < pos + shipSize; i++)
        {
            cells[0][i] = 1;
        }
    }
    
    public int GuessPos(int x, int y)
    {
        if(guesses[x][y] == 1)
        {
            // You already guessed this!
            return 2;
        }

        guesses[x][y] = 1;

        if(cells[x][y] == 0)
        {
            // Miss :(
            misses++;
            return 0;
        }
        
        if(cells[x][y] == 1)
        {
            // Hit!
            hits++;
            return 1;
        }

        // Error
        return -1;
    }

    public boolean GetState()
    {
        if(hits >= shipSize)
        {
            // Game ended
            state = false;
        }

        return state;
    }
    
    public double GetScore()
    {
        return (double) (cols - misses) / (double) cols; 
    }

    public int GetRows()
    {
        return rows;
    }

    public int GetColumns()
    {
        return cols;
    }

    public int GetShipSize()
    {
        return shipSize;
    }
}
