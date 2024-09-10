import java.awt.BorderLayout;

import javax.swing.*; // JFrame, Jpanel - Swing library

/**
 * GameWindow class extends JFrame class
 * This class is used to create the JFrame window for the game of life
 * @author Daniel Paxton
 * @version 1.0
 * @since 1.0
 * @see GameBoard
 * @see App
 */
public class GameWindow extends JFrame { // GameWindow class extends JFrame class
    
    private JPanel gamePanel; // JPanel object

    public GameWindow() {

        // Set the title of the JFrame
        this.setTitle("Game of Life");

        // Set the layout of the JFrame
        this.setLayout(new BorderLayout());

        // Make a new GameBoard object and assign it to the gamPanel
        this.gamePanel = new GameBoard(); 
        this.add(gamePanel, BorderLayout.CENTER); // Add the gamePanel to the JFrame

        // Make a button that will be used to update the game board
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            ((GameBoard) gamePanel).update(); // Cast the gamePanel to a GameBoard object and call the update method
            gamePanel.repaint(); // Repaint the gamePanel
        });

        this.add(updateButton, BorderLayout.SOUTH); // Add the button to the JFrame

        // Set the size of the JFrame
        this.setSize(610, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
