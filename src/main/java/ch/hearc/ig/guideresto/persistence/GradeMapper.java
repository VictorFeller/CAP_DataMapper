package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class GradeMapper {
    private static final String QUERY_ALL = "SELECT NUMERO, NOTE, FK_COMM, FK_CRIT FROM NOTES WHERE FK_COMM = ?";
    public static final String QUERY_INSERT = "INSERT INTO NOTES (NOTE, FK_COMM, FK_CRIT) VALUES (?,?, ?)";

    public static void insert(Grade grade) {
        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_INSERT)) {
            prepareStatement.setInt(1, grade.getGrade());
            prepareStatement.setInt(2, grade.getEvaluation().getId());
            prepareStatement.setInt(3, grade.getCriteria().getId());
            prepareStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<Grade> findAllById(CompleteEvaluation eval){
        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_ALL)) {
            prepareStatement.setInt(1, eval.getId());
            ResultSet resultSet = prepareStatement.executeQuery();
            Set<Grade> grades = new HashSet<>();
            while(resultSet.next()) {
                grades.add(new Grade(
                        resultSet.getInt("NUMERO"),
                        resultSet.getInt("NOTE"),
                        eval,
                        EvaluationCriteriaMapper.findById(resultSet.getInt("FK_CRIT"))));

            }
            return grades;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
