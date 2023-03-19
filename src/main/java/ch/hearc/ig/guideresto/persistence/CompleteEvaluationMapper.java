package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.Evaluation;
import ch.hearc.ig.guideresto.business.Restaurant;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CompleteEvaluationMapper {
    public static final String QUERY_BY_RESTAURANT_NUMERO = "SELECT NUMERO, DATE_EVAL, COMMENTAIRE, NOM_UTILISATEUR, FK_REST FROM COMMENTAIRES WHERE FK_REST = ?";
    public static final String QUERY_INSERT = "INSERT INTO COMMENTAIRES (DATE_EVAL, COMMENTAIRE, NOM_UTILISATEUR, FK_REST) VALUES (?, ?, ?, ?)";
    public static Set<Evaluation> findByRestaurantNumero(Restaurant restaurant) {
        try (Connection cnn = DBOracleDriverManager.getConnection();
             PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_BY_RESTAURANT_NUMERO)){
            prepareStatement.setInt(1, restaurant.getId());
            ResultSet resultSet = prepareStatement.executeQuery();
            Set<Evaluation> completeEvaluation = new HashSet<>();
            while (resultSet.next()) {
                completeEvaluation.add(new CompleteEvaluation(
                        resultSet.getInt("NUMERO"),
                        resultSet.getDate("DATE_EVAL").toLocalDate(),
                        restaurant,
                        resultSet.getString("COMMENTAIRE"),
                        resultSet.getString("NOM_UTILISATEUR")));
            }

            return completeEvaluation;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void insert(CompleteEvaluation completeEvaluation) {
        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_INSERT)) {
            prepareStatement.setDate(1, Date.valueOf(completeEvaluation.getVisitDate()));
            prepareStatement.setString(2, completeEvaluation.getComment());
            prepareStatement.setString(3, completeEvaluation.getUsername());
            prepareStatement.setInt(4, completeEvaluation.getRestaurant().getId());

            prepareStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
