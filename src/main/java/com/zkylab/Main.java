package com.zkylab;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import com.zkylab.common.GamePanel;

/**
 * The main entry point of the application.
 * Initializes the game window and starts the game.
 */
public class Main {

    public static JFrame window;

    /**
     * Main method that sets up and displays the game window.
     *
     * @param args Command-line arguments (not used).
     * @throws Exception If any errors occur during initialization.
     */
    public static void main(String[] args) throws Exception {
        // Create and configure the main window
        window = new JFrame("Resilience");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Set the window icon
        new Main().setIcon();

        // Create and add the game panel
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // Load game configuration and setup the game
        gamePanel.config.loadConfig();
        if (gamePanel.fullscreenOn) {
            window.setUndecorated(true); // Fullscreen mode
        }

        // Pack and display the window
        window.pack();
        window.setVisible(true);
        window.setLocationRelativeTo(null);

        // Initialize and start the game
        gamePanel.setupGame();
        gamePanel.startGameThread();

    }

    /**
     * Sets the icon image for the game window.
     */
    public void setIcon() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("player/mc_walk_down_1.png"));
        window.setIconImage(icon.getImage());
    }
}
