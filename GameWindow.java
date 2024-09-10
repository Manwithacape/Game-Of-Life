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
    private boolean isTimerRunning = false; // boolean variable to check if the timer is running

    public GameWindow() {

        // Set the title of the JFrame
        this.setTitle("Game of Life");

        // Set the layout of the JFrame
        this.setLayout(new BorderLayout());

        // Make a new GameBoard object and assign it to the gamPanel
        this.gamePanel = new GameBoard(); 
        this.add(gamePanel, BorderLayout.CENTER); // Add the gamePanel to the JFrame


        // ------ BUTTON PANEL ------
        // Make a JPanel object to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        // Make a button that will be used to update the game board
        JButton updateButton = new JButton(">");
        updateButton.addActionListener(e -> {
            if (!isTimerRunning) { // If the timer is not running
                ((GameBoard) gamePanel).update(); // Cast the gamePanel to a GameBoard object and call the update method
                gamePanel.repaint(); // Repaint the gamePanel
            }
        });

        // Add a timer to the JFrame
        Timer timer = new Timer(100, e -> {
            if (isTimerRunning) {
                ((GameBoard) gamePanel).update(); // Cast the gamePanel to a GameBoard object and call the update method
                gamePanel.repaint(); // Repaint the gamePanel
            }
        });

        // Make a button that will be used to start the timer
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            isTimerRunning = !isTimerRunning; // Set the isTimerRunning variable to true

            // Change the text of the button based on the state of the timer
            if (isTimerRunning) {
                startButton.setText("Stop");
            } else {
                startButton.setText("Start");
            }   

            timer.start(); // Start the timer
        });

        // Make a button that will be used to clear the game board
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            ((GameBoard) gamePanel).clearBoard(); // Cast the gamePanel to a GameBoard object and call the clear method
            gamePanel.repaint(); // Repaint the gamePanel
        });

        // Add the buttons to the buttonPanel
        buttonPanel.add(clearButton, BorderLayout.WEST);
        buttonPanel.add(updateButton, BorderLayout.EAST);
        buttonPanel.add(startButton, BorderLayout.CENTER);

        // Add the buttonPanel to the JFrame
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Set the size of the JFrame
        this.setSize(620, 665);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
