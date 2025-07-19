package com.yourorg.auth.demo;

import com.yourorg.auth.demo.controller.AuthController;
import com.yourorg.auth.demo.view.LoginPanel;
import com.yourorg.auth.demo.view.RegisterPanel;
import com.yourorg.auth.domain.repository.InMemoryUserRepository;
import com.yourorg.auth.domain.security.Pbkdf2PasswordEncoder;
import com.yourorg.auth.domain.security.UuidTokenService;
import com.yourorg.auth.domain.service.AuthManagerImpl;
import java.awt.*;
import javax.swing.*;

/**
 * Main application frame for the authentication demo.
 * <p>
 * Provides a simple UI for user login and registration using a card layout.
 * Handles switching between login and registration panels and displays authentication results.
 * </p>
 */
public class AuthFrame extends JFrame {
    /** CardLayout for switching between login and registration panels. */
    private final CardLayout cards = new CardLayout();
    /** Main content panel containing the login and registration panels. */
    private final JPanel content = new JPanel(cards);

    /**
     * Constructs the authentication demo frame and initializes the UI.
     */
    public AuthFrame() {
        setTitle("Auth Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);

        AuthController controller = new AuthController(
                new AuthManagerImpl(
                        new InMemoryUserRepository(),
                        new Pbkdf2PasswordEncoder(),
                        new UuidTokenService()
                )
        );

        LoginPanel loginPanel = new LoginPanel(controller);
        RegisterPanel registerPanel = new RegisterPanel(controller);

        // Set up listeners for login panel events
        loginPanel.setAuthListener(new LoginPanel.AuthListener() {
            /**
             * Called when authentication is successful.
             *
             * @param token the authentication token
             */
            public void onAuthSuccess(String token) {
                JOptionPane.showMessageDialog(AuthFrame.this,
                        "Authenticated! Token: " + token);
            }
            /**
             * Called when the user wants to switch to the registration panel.
             */
            public void onSwitchToRegister() {
                cards.show(content, "register");
            }
        });

        // Set up listeners for registration panel events
        registerPanel.setRegisterListener(new RegisterPanel.RegisterListener() {
            /**
             * Called when registration is successful.
             */
            public void onRegisterSuccess() {
                JOptionPane.showMessageDialog(AuthFrame.this, "Registered! You can now login.");
                cards.show(content, "login");
            }
            /**
             * Called when the user wants to switch to the login panel.
             */
            public void onSwitchToLogin() {
                cards.show(content, "login");
            }
        });

        content.add(loginPanel, "login");
        content.add(registerPanel, "register");
        add(content);
        cards.show(content, "login");
    }
}