package ui;

import dao.QuizDAO;
import model.Quiz;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizSelectionFrame extends JFrame {
    private User user;
    private QuizDAO quizDAO = new QuizDAO();

    public QuizSelectionFrame(User user) {
        this.user = user;
        setTitle("Select Quiz - " + user.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        init();
    }

    private void init() {
        List<Quiz> quizzes = quizDAO.getAllQuizzes();
        DefaultListModel<Quiz> model = new DefaultListModel<>();
        for (Quiz q : quizzes) model.addElement(q);
        JList<Quiz> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton startBtn = new JButton("Start Quiz");
        startBtn.addActionListener(e -> {
            Quiz selected = list.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a quiz first");
                return;
            }
            new QuizFrame(user, selected).setVisible(true);
            this.dispose();
        });

        setLayout(new BorderLayout(10,10));
        add(new JScrollPane(list), BorderLayout.CENTER);
        add(startBtn, BorderLayout.SOUTH);
    }
}
