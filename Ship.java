import java.util.ArrayList;

public class Ship
{
    ArrayList<int[]> cells = new ArrayList<int[]>();

    public void add(int x, int y)
    {
        cells.add(new int[] { x, y });
    }

    public boolean guess(int x, int y, boolean removeCell)
    {
        for (int[] pos : cells)
        {
            if (pos[0] == x && pos[1] == y)
            {
                if (removeCell)
                {
                    cells.remove(pos);
                }
                
                return true;
            }
        }
        
        return false;
    }

    public boolean sunk()
    {
        if (cells.size() == 0)
        {
            return true;
        }
        
        return false;
    }
}