

 
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
 
public class Sudoku 
{
    // Dimension of the game grid
    public static final int SIZE = 9;
 
    // Dimension of a subgrid
    public static final int SUBGRID = 3;
 
    //current game grid
    public int[][] game;   
 
    // original game grid
    public int[][] originalGame; 
 

    public Sudoku(String puzzleFile)
    {
    
        try
        {
            game = new int[SIZE][SIZE];
            originalGame = new int[SIZE][SIZE];
 
            
            File file = new File(puzzleFile);
            Scanner in = new Scanner(file);
            
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    game[i][j] = in.nextInt();
                    originalGame[i][j] = game[i][j];
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("FileNotFound: " + e.getMessage());
        }   
    }

    public void setZero(int[] array)
    {
        for (int i = 0; i < SIZE; i++) {
            array[i] = 0;
        }
    }
 
   
    public boolean rowsComplete(int[] valSeen)
    {     
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                if (game[i][j] == -1)
                {
                    return false;
                }
                else if (valSeen[game[i][j] - 1] != 1)
                {
                    valSeen[game[i][j] - 1] = 1;
                }
                else {
                    return false;
                }
            }
            setZero(valSeen);
        }
   
        return true;
    } 

 
    
    public boolean columnsComplete(int[] valSeen)
    {
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                if (game[j][i] == -1) {
                    return false;
                }
                else if (valSeen[game[j][i] - 1] != 1) {
                    valSeen[game[j][i] - 1] = 1;
                }
                else {
                    return false;
                }
            
            }
            setZero(valSeen);
        }
        return true; 
    }
    
    
    public boolean subgridsComplete(int[] valSeen)
    {
        for (int i = 0; i < SIZE; i = i + 3)
        {
            for (int j = 0; j < SIZE; j = j + 3)
            {
                this.setZero(valSeen);
                for (int k = i; k < i + 3; k++)
                {
                    for (int l = j; l < j + 3; l++)
                    {
                        if (game[k][l] == -1) {
                            return false;
                        }
                        else if (valSeen[game[k][l] - 1] != 1)
                        {
                            valSeen[game[k][l] - 1] = 1;
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
 

    public boolean isComplete()
    {
        int valSeen[] = new int [SIZE];
        setZero(valSeen);
     
        boolean rows;
        boolean cols;
        boolean subgrids;
     
        rows = rowsComplete(valSeen);
        setZero(valSeen);
        cols = columnsComplete(valSeen);
        setZero(valSeen);
        subgrids = subgridsComplete(valSeen);
        if (rows == true && cols == true && subgrids == true) {
            return true;
        }
        else
        {
            return false;
        }  
    }
 

    public String makeHeader()
    {
        String header = "   ";
        for (char c = 'a'; c <= 'i'; c++)
        {
            header = header + "| " + c + " ";
        }
        return header;
    }
 
 
    public void print()
    {
        char c = 'a';
        String h = makeHeader();
        System.out.printf(h);
        System.out.printf("\n");
        for (int i = 0; i < SIZE; i++)
        {
            System.out.printf(" %c", c);
            for (int j = 0; j < SIZE; j++)
            {
                if (game[i][j] == -1)
                    System.out.printf(" | _");
                else
                    System.out.printf(" | %d", game[i][j]); 
            }
            System.out.printf(" \n");
            c++;
        }
    }

    public void move(String row, String col, int val)
    {
        String s = "abcdefghi";
        if (originalGame[s.indexOf(row)][s.indexOf(col)] == -1) {
            game[s.indexOf(row)][s.indexOf(col)] = val;
        }
        else {
            System.out.println("Fixed location. Cannot change value!");
        }
    }
 
  
    public static void main(String[] args)
    {
        
        String a;
        String b;
        int i;
        
        Sudoku game = new Sudoku(args[0]);

        if (game.isComplete() == false)
        {
            game.print();
            System.out.println("Puzzle Incomplete!");
        }
        else 
        {
            game.print();
        }

        Scanner in = new Scanner(System.in);
        while (game.isComplete() == false)
        {
            System.out.println("Enter new value <row col val> :");
            a = in.next();
            b = in.next();
            i = in.nextInt();
            game.move(a, b, i);
            game.print();
            if (game.isComplete() == false) {
                System.out.println("Puzzle Incomplete!");
            }
        }
        if (game.isComplete() == true) {
            System.out.println("Puzzle Complete!");
        }

    }
}

