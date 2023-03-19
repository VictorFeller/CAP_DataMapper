package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.business.Evaluation;
import ch.hearc.ig.guideresto.business.Restaurant;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class BasicEvaluationMapper {
    public static final String QUERY_BY_RESTAURANT_NUMERO = "SELECT NUMERO, APPRECIATION, DATE_EVAL, ADRESSE_IP, FK_REST FROM LIKES WHERE FK_REST = ?";
    public static final String QUERY_INSERT = "INSERT INTO LIKES (APPRECIATION, DATE_EVAL, ADRESSE_IP, FK_REST) VALUES (?, ?, ?, ?)";

    public static void insert(BasicEvaluation basicEvaluation) {
        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_INSERT)) {
            prepareStatement.setString(1, DAOUtils.booleanToJavaString(basicEvaluation.isLikeRestaurant()));
            prepareStatement.setDate(2, Date.valueOf(basicEvaluation.getVisitDate()));
            prepareStatement.setString(3, basicEvaluation.getIpAddress());
            prepareStatement.setInt(4, basicEvaluation.getRestaurant().getId());

            prepareStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<Evaluation> findByRestaurantNumero(Restaurant restaurant) {
        try (Connection cnn = DBOracleDriverManager.getConnection();
             PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_BY_RESTAURANT_NUMERO)){
            prepareStatement.setInt(1, restaurant.getId());
            ResultSet resultSet = prepareStatement.executeQuery();
            Set<Evaluation> basicEvaluation = new HashSet<>();
            while (resultSet.next()) {
               basicEvaluation.add(new BasicEvaluation(
                        resultSet.getInt("NUMERO"),
                        resultSet.getDate("DATE_EVAL").toLocalDate(),
                        restaurant,
                        DAOUtils.charToJavaBoolean(resultSet.getString("APPRECIATION")),
                        resultSet.getString("ADRESSE_IP")));
            }

            return basicEvaluation;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
