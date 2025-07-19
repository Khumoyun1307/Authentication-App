package com.yourorg.auth.demo;
import javax.swing.SwingUtilities;

/**
 * Entry point for the authentication demo application.
 * <p>
 * Launches the {@link AuthFrame} on the Event Dispatch Thread.
 * </p>
 */
public class Main {
    /**
     * Main method to start the authentication demo application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AuthFrame().setVisible(true);
        });
    }
}
