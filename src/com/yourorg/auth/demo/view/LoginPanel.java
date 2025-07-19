package com.yourorg.auth.demo.view;

import com.yourorg.auth.demo.controller.AuthController;
import com.yourorg.auth.domain.exception.AuthException;
import java.awt.*;
import javax.swing.*;

/**
 * Login panel for user authentication in the demo application.
 * <p>
 * Provides fields for username and password input, and buttons for login and switching to registration.
 * Notifies an {@link AuthListener} on successful authentication or when the user wants to register.
 * </p>
 */
public class LoginPanel extends JPanel {
    /**
     * Listener interface for authentication events.
     */
    public interface AuthListener {
        /**
         * Called when authentication is successful.
         *
         * @param token the authentication token
         */
        void onAuthSuccess(String token);

        /**
         * Called when the user wants to switch to the registration panel.
         */
        void onSwitchToRegister();
    }

    private final AuthController controller;
    private AuthListener authListener;
    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Constructs a new LoginPanel with the specified authentication controller.
     *
     * @param controller the authentication controller to use for login actions
     */
    public LoginPanel(AuthController controller) {
        this.controller = controller;
        initComponents();
    }

    /**
     * Initializes the UI components and event handlers for the login panel.
     */
    private void initComponents() {
        setLayout(new GridLayout(4, 1, 5, 5));
        add(new JLabel("Login"));
        usernameField = new JTextField();
        add(usernameField);
        passwordField = new JPasswordField();
        add(passwordField);
        JPanel buttons = new JPanel(new FlowLayout());
        JButton loginBtn = new JButton("Login");
        JButton toRegBtn = new JButton("Go to Register");
        buttons.add(loginBtn);
        buttons.add(toRegBtn);
        add(buttons);

        // Handle login button click
        loginBtn.addActionListener(e -> {
            try {
                String token = controller.login(
                        usernameField.getText(), new String(passwordField.getPassword())
                );
                if (authListener != null) authListener.onAuthSuccess(token);
            } catch (AuthException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Handle switch to register button click
        toRegBtn.addActionListener(e -> {
            if (authListener != null) authListener.onSwitchToRegister();
        });
    }

    /**
     * Sets the authentication listener for this panel.
     *
     * @param listener the listener to notify of authentication events
     */
    public void setAuthListener(AuthListener listener) {
        this.authListener = listener;
    }
}