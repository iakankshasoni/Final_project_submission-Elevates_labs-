package app;

import ui.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Run UI on EDT
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
