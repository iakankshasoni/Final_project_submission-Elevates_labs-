package dao;

import db.DBConnection;
import model.Question;
import model.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizDAO {

    public List<Quiz> getAllQuizzes() {
        List<Quiz> list = new ArrayList<>();
        String sql = "SELECT * FROM quizzes";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Quiz(rs.getInt("id"), rs.getString("title"),
                                  rs.getString("description"), rs.getInt("time_limit_minutes")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Question> getRandomQuestionsForQuiz(int quizId, int limit) {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE quiz_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quizId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Question q = new Question();
                    q.setId(rs.getInt("id"));
                    q.setQuizId(rs.getInt("quiz_id"));
                    q.setQuestionText(rs.getString("question_text"));
                    q.setOptionA(rs.getString("option_a"));
                    q.setOptionB(rs.getString("option_b"));
                    q.setOptionC(rs.getString("option_c"));
                    q.setOptionD(rs.getString("option_d"));
                    q.setCorrectOption(rs.getString("correct_option").charAt(0));
                    list.add(q);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.shuffle(list);
        if (limit > 0 && limit < list.size()) {
            return new ArrayList<>(list.subList(0, limit));
        }
        return list;
    }

    public boolean saveResult(int userId, int quizId, int score, int total) {
        String sql = "INSERT INTO results (user_id, quiz_id, score, total_questions) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, quizId);
            ps.setInt(3, score);
            ps.setInt(4, total);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
