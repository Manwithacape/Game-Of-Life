import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * GameBoard class extends JPanel class
 * This class is used to draw the game board, updating the game board
 * @author Daniel Paxton
 * @version 1.0
 * @since 1.0
 * @see GameWindow
 * @see App
 */
public class GameBoard extends JPanel {
    private boolean[][] gameBoard;
    private boolean[][] nextGeneration;

    private int cellSize = 60;  // Size of each cell in pixels

    /**
     * Constructor for the GameBoard class
     * Initializes the game board and the next generation 2D arrays
     */
    public GameBoard() {
        this.setSize(600, 600); // Set the size of the JPanel same as the jframe will use
        this.gameBoard = new boolean[10][10]; // Create a new 2D array for the game board
        this.nextGeneration = new boolean[10][10]; // Create a new 2D array for the next generation

        // debug data to test the game board
        // this.gameBoard[1][1] = true;
        // this.gameBoard[1][2] = true;
        // this.gameBoard[1][3] = true;
        // this.gameBoard[2][2] = true;
        // this.gameBoard[2][3] = true;

        // Add a mouse listener to the JPanel and update the game board based on the user input
        this.addMouseListener(new MouseAdapter() { // Add a mouse listener to the JPanel
            @Override
            public void mouseClicked(MouseEvent evt) { // When the mouse is clicked
                int x = evt.getX() / cellSize; // Get the x coordinate of the mouse click and divide it by the cell size to get the cell index
                int y = evt.getY() / cellSize; // Get the y coordinate of the mouse click and divide it by the cell size to get the cell index
                gameBoard[x][y] = !gameBoard[x][y]; // Toggle the value of the cell at the index (x, y)
                repaint(); // Repaint the JPanel
            }
        });
    }

    /**
     * The core logic of the game of life is implemented in this method
     * This method updates the game board to the next generation based on the rules of the game according to John Conway
     * The rules are as follows:
     *  1. Any live cell with fewer than two live neighbours dies, as if by underpopulation.
     *  2. Any live cell with two or three live neighbours lives on to the next generation.
     *  3. Any live cell with more than three live neighbours dies, as if by overpopulation.
     */
    public void update() {

        // Loop through the game board and update the next generation
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[0].length; y++) {
                // Count the number of alive neighbours of the cell at (x, y)
                int aliveNeighbours = countAliveNeighbours(x, y);

                // Check if the cell at (x, y) is alive
                if (isAlive(x, y)) {
                    // If the cell has less than 2 or more than 3 alive neighbours, it dies
                    if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                        nextGeneration[x][y] = false;
                    } else {
                        nextGeneration[x][y] = true;
                    }
                } else {
                    // If the cell has exactly 3 alive neighbours, it becomes alive
                    if (aliveNeighbours == 3) {
                        nextGeneration[x][y] = true;
                    } else {
                        nextGeneration[x][y] = false;
                    }
                }
            }
        }

        // Copy the next generation to the game board
        gameBoard = nextGeneration;  // Let the garbage collector handle the old game board
        nextGeneration = new boolean[10][10]; // Create a new 2D array for the next generation


    }

    /**
     * Method to check if the given index is within the bounds of the 2D array
     * @param indexX first index
     * @param indexY second index
     * @param array 2D array
     * @return <code>true</code> if the index is within the bounds of the array, <code>false</code> otherwise
     */
    public static boolean indexInBounds(int indexX, int indexY, boolean[][] array) {
        return indexX >= 0 && indexX < array.length && indexY >= 0 && indexY < array[0].length;
    }

    /**
     * Method to check if the given index is within the bounds of the 2D array
     * @param x first index
     * @param y second index
     * @return <code>true</code> if the index is within the bounds of the array, <code>false</code> otherwise
     */
    private boolean isAlive(int x, int y) {
        return gameBoard[x][y];
    }

    /**
     * Method to count the number of alive neighbours of a cell at position (x, y)
     * @param x first index
     * @param y second index
     * @return the number of alive neighbours of the cell at position (x, y) (max 8, min 0)
     */
    private int countAliveNeighbours(int x, int y) {
        // Initialize the count of alive neighbours to 0
        int count = 0;

        // Loop through the 8 neighbours of the cell at (x, y)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Skip the cell at (x, y) itself
                if (i == 0 && j == 0) {
                    continue;
                }

                // Check if the neighbour is alive
                if (indexInBounds(x + i, y + j, gameBoard) && gameBoard[x + i][y + j]) {
                    count++;
                }
            }
        }

        // Return the count of alive neighbours
        return count;
    }

    @Override
    public void paintComponent(Graphics g) {
        // Call the paintComponent method of the parent class
        super.paintComponent(g);

        // draw a background color
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Loop through the game board and draw the cells on top of the background
        for (int x = 0; x < gameBoard.length; x++) {  // Loop through the rows (x) of the game board
            for (int y = 0; y < gameBoard[0].length; y++) {  // Loop through the columns (y) within the current row (x)

                // Set the color of the cell based on the value of the cell
                if (isAlive(x, y)) {  // If the cell is alive
                    g.setColor(Color.BLACK);
                } else {                // If the cell is not alive (dead)
                    g.setColor(Color.WHITE);
                }

                // Draw the cell at (x, y) position but scaled up by cellSize (60 pixels) - a pixel so the background color is visible
                g.fillRect(x * cellSize, y * cellSize, cellSize-1, cellSize-1);
            }
        }
    }
}