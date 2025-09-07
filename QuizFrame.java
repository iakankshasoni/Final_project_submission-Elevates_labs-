package ui;

import dao.QuizDAO;
import model.Question;
import model.Quiz;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class QuizFrame extends JFrame {
    private User user;
    private Quiz quiz;
    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;

    private JLabel lblQuestion;
    private JRadioButton a,b,c,d;
    private ButtonGroup bg;
    private JButton nextBtn;
    private JLabel timerLabel;
    private Timer swingTimer;
    private int remainingSeconds;

    private QuizDAO quizDAO = new QuizDAO();

    public QuizFrame(User user, Quiz quiz) {
        this.user = user;
        this.quiz = quiz;
        setTitle("Quiz: " + quiz.getTitle());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        loadQuestions();
        initUI();
        startTimer();
    }

    private void loadQuestions() {
        // load all questions and randomize; here limiting to 10 or all
        questions = quizDAO.getRandomQuestionsForQuiz(quiz.getId(), 10);
    }

    private void initUI() {
        lblQuestion = new JLabel();
        a = new JRadioButton();
        b = new JRadioButton();
        c = new JRadioButton();
        d = new JRadioButton();
        bg = new ButtonGroup();
        bg.add(a); bg.add(b); bg.add(c); bg.add(d);

        nextBtn = new JButton("Next");
        nextBtn.addActionListener((ActionEvent e) -> nextQuestion());

        timerLabel = new JLabel("Time: --:--");
        JPanel top = new JPanel(new BorderLayout());
        top.add(timerLabel, BorderLayout.EAST);

        JPanel center = new JPanel(new GridLayout(5,1,5,5));
        center.add(lblQuestion);
        center.add(a); center.add(b); center.add(c); center.add(d);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(nextBtn, BorderLayout.SOUTH);

        showQuestion();
    }

    private void showQuestion() {
        if (currentIndex >= questions.size()) {
            finishQuiz();
            return;
        }
        Question q = questions.get(currentIndex);
        lblQuestion.setText("<html><b>Q"+(currentIndex+1)+":</b> "+q.getQuestionText()+"</html>");
        a.setText("A. " + q.getOptionA());
        b.setText("B. " + q.getOptionB());
        c.setText("C. " + q.getOptionC());
        d.setText("D. " + q.getOptionD());
        bg.clearSelection();
    }

    private void nextQuestion() {
        if (currentIndex >= questions.size()) {
            finishQuiz();
            return;
        }
        Question q = questions.get(currentIndex);
        char chosen = ' ';
        if (a.isSelected()) chosen = 'A';
        if (b.isSelected()) chosen = 'B';
        if (c.isSelected()) chosen = 'C';
        if (d.isSelected()) chosen = 'D';
        if (chosen == q.getCorrectOption()) score++;
        currentIndex++;
        if (currentIndex < questions.size()) showQuestion();
        else finishQuiz();
    }

    private void startTimer() {
        remainingSeconds = quiz.getTimeLimitMinutes() * 60;
        timerLabel.setText(formatTime(remainingSeconds));
        swingTimer = new Timer(1000, (ActionEvent e) -> {
            remainingSeconds--;
            timerLabel.setText(formatTime(remainingSeconds));
            if (remainingSeconds <= 0) {
                swingTimer.stop();
                JOptionPane.showMessageDialog(this, "Time is up!");
                finishQuiz();
            }
        });
        swingTimer.start();
    }

    private String formatTime(int secs) {
        int m = secs / 60;
        int s = secs % 60;
        return String.format("Time: %02d:%02d", m, s);
    }

    private void finishQuiz() {
        if (swingTimer != null) swingTimer.stop();
        quizDAO.saveResult(user.getId(), quiz.getId(), score, questions.size());
        new ResultFrame(user, quiz, questions, score).setVisible(true);
        this.dispose();
    }
}
