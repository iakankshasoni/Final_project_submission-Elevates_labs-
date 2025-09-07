package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("Online Quiz - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel p = new JPanel(new GridLayout(5,1,5,5));
        p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        p.add(new JLabel("Username:"));
        p.add(usernameField);
        p.add(new JLabel("Password:"));
        p.add(passwordField);

        JPanel btns = new JPanel();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        btns.add(loginBtn);
        btns.add(registerBtn);
        p.add(btns);

        loginBtn.addActionListener((ActionEvent e) -> login());
        registerBtn.addActionListener((ActionEvent e) -> register());

        add(p);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username/password");
            return;
        }

        User user = userDAO.findByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            // proceed to quiz selection
            SwingUtilities.invokeLater(() -> {
                new QuizSelectionFrame(user).setVisible(true);
            });
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }

    private void register() {
        String username = JOptionPane.showInputDialog(this, "Choose username:");
        if (username == null || username.trim().isEmpty()) return;
        String fullName = JOptionPane.showInputDialog(this, "Full name:");
        if (fullName == null) fullName = "";
        String password = JOptionPane.showInputDialog(this, "Choose password:");
        if (password == null || password.trim().isEmpty()) return;

        User u = new User();
        u.setUsername(username.trim());
        u.setPassword(password); // Note: hashing recommended in production
        u.setFullName(fullName);

        if (userDAO.create(u)) {
            JOptionPane.showMessageDialog(this, "Registered successfully. You can log in now.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to register. Username may already exist.");
        }
    }
}
