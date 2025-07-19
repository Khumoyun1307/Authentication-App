package com.yourorg.auth.demo.view;

import com.yourorg.auth.demo.controller.AuthController;
import com.yourorg.auth.domain.exception.AuthException;
import java.awt.*;
import javax.swing.*;

/**
 * Registration panel for user sign-up in the demo application.
 * <p>
 * Provides fields for username and password input, and buttons for registration and switching to login.
 * Notifies a {@link RegisterListener} on successful registration or when the user wants to switch to login.
 * </p>
 */
public class RegisterPanel extends JPanel {
    /**
     * Listener interface for registration events.
     */
    public interface RegisterListener {
        /**
         * Called when registration is successful.
         */
        void onRegisterSuccess();

        /**
         * Called when the user wants to switch to the login panel.
         */
        void onSwitchToLogin();
    }

    private final AuthController controller;
    private RegisterListener registerListener;
    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Constructs a new RegisterPanel with the specified authentication controller.
     *
     * @param controller the authentication controller to use for registration actions
     */
    public RegisterPanel(AuthController controller) {
        this.controller = controller;
        initComponents();
    }

    /**
     * Initializes the UI components and event handlers for the registration panel.
     */
    private void initComponents() {
        setLayout(new GridLayout(4, 1, 5, 5));
        add(new JLabel("Register"));
        usernameField = new JTextField();
        add(usernameField);
        passwordField = new JPasswordField();
        add(passwordField);
        JPanel buttons = new JPanel(new FlowLayout());
        JButton regBtn = new JButton("Register");
        JButton toLoginBtn = new JButton("Go to Login");
        buttons.add(regBtn);
        buttons.add(toLoginBtn);
        add(buttons);

        // Handle register button click
        regBtn.addActionListener(e -> {
            try {
                controller.register(
                        usernameField.getText(), new String(passwordField.getPassword())
                );
                if (registerListener != null) registerListener.onRegisterSuccess();
            } catch (AuthException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Handle switch to login button click
        toLoginBtn.addActionListener(e -> {
            if (registerListener != null) registerListener.onSwitchToLogin();
        });
    }

    /**
     * Sets the registration listener for this panel.
     *
     * @param listener the listener to notify of registration events
     */
    public void setRegisterListener(RegisterListener listener) {
        this.registerListener = listener;
    }
}
