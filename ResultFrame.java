package ui;

import model.Question;
import model.Quiz;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResultFrame extends JFrame {

    public ResultFrame(User user, Quiz quiz, List<Question> questions, int score) {
        setTitle("Result - " + user.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        init(quiz, questions, score);
    }

    private void init(Quiz quiz, java.util.List<Question> questions, int score) {
        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("<html><h2>" + quiz.getTitle() + " - Result</h2></html>"), BorderLayout.WEST);
        top.add(new JLabel("Score: " + score + " / " + questions.size()), BorderLayout.EAST);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String correct = String.valueOf(q.getCorrectOption());
            String text = "<html><b>Q"+(i+1)+":</b> " + q.getQuestionText()
                    + "<br/><b>Correct:</b> " + correct + " - " + optionText(q, q.getCorrectOption())
                    + "</html>";
            JLabel lbl = new JLabel(text);
            lbl.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            listPanel.add(lbl);
        }

        JScrollPane sp = new JScrollPane(listPanel);

        setLayout(new BorderLayout(10,10));
        add(top, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
    }

    private String optionText(Question q, char opt) {
        switch (opt) {
            case 'A': return q.getOptionA();
            case 'B': return q.getOptionB();
            case 'C': return q.getOptionC();
            case 'D': return q.getOptionD();
        }
        return "";
    }
}
