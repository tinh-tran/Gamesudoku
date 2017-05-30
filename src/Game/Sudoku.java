package Game;

import Starter.SolutionReader;
import java.io.FileNotFoundException;

public class Sudoku {

    private final int[][] userBoard; //Mảng 2 chiều cho game sudoku
    private int[][] solution;
    private final SolutionReader reader;

    public Sudoku(Level levelType) throws FileNotFoundException {
        solution = new int[9][9];// khai báo số phần tử của mảng
        reader = new SolutionReader(levelType);
        solution = reader.loadSolution(solution);
        userBoard = reader.loadCleanedBoard(solution);
    }

    public int[][] getUserBoard() {
        return userBoard;
    }

    public int[][] getSolution() {
        return solution;
    }
}
