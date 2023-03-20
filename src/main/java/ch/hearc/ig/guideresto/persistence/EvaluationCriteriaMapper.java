package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.EvaluationCriteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class EvaluationCriteriaMapper {
    private static final String QUERY_ALL = "SELECT NUMERO, NOM, DESCRIPTION FROM CRITERES_EVALUATION";
    private static final String QUERY_BY_NUMERO = "SELECT NUMERO, NOM, DESCRIPTION FROM CRITERES_EVALUATION WHERE NUMERO = ?";
    public static Set<EvaluationCriteria> findAll() {
        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_ALL)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            Set<EvaluationCriteria> evaluationCriterias = new HashSet<>();
            while(resultSet.next()) {
                evaluationCriterias.add(new EvaluationCriteria(
                        resultSet.getInt("NUMERO"),
                        resultSet.getString("NOM"),
                        resultSet.getString("DESCRIPTION")));

            }
            return evaluationCriterias;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static EvaluationCriteria findById(int evaluationCriteriaNumero) {
        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement pStmt = cnn.prepareStatement(QUERY_BY_NUMERO)) {
            pStmt.setInt(1, evaluationCriteriaNumero);
            ResultSet resultSet = pStmt.executeQuery();
            EvaluationCriteria evaluationCriteria = null;
            while(resultSet.next()) {
                evaluationCriteria = new EvaluationCriteria(
                        resultSet.getInt("NUMERO"),
                        resultSet.getString("NOM"),
                        resultSet.getString("DESCRIPTION")
                );
            }
            resultSet.close();
            return evaluationCriteria;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
