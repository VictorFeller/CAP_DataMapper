package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.Evaluation;
import ch.hearc.ig.guideresto.business.Restaurant;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class RestaurantDAO {
    private static final String QUERY_ALL = "SELECT NUMERO, NOM, ADRESSE, DESCRIPTION, SITE_WEB, FK_TYPE, FK_VILL FROM RESTAURANTS";
    public static final String QUERY_DELETE = "DELETE FROM RESTAURANTS WHERE NUMERO = ?";
    public static final String QUERY_INSERT = "INSERT INTO RESTAURANTS (NOM, ADRESSE, DESCRIPTION, SITE_WEB, FK_TYPE, FK_VILL) VALUES (?,?, ?, ?, ?, ?) RETURNING NUMERO INTO ?";

    public static Set<Restaurant> findAll() {
        try(Connection cnn = DBOracleDriverManager.getConnection();
                PreparedStatement statement = cnn.prepareStatement(QUERY_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            Set<Restaurant> restaurants = new HashSet<>();
            while(resultSet.next()) {
                //TODO
                // Comment recupérer le champ evaluations
                Restaurant restaurant = new Restaurant(
                        resultSet.getInt("NUMERO"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getString("site_web"),
                        resultSet.getString("adresse"),
                        CityDAO.findByNumero(resultSet.getInt("FK_VILL")),
                        RestaurantTypeDAO.findByNumero(resultSet.getInt("FK_TYPE")));
                //Ajouter les evaluations
                restaurant.getEvaluations().addAll(BasicEvaluationDAO.findByRestaurantNumero(restaurant));
                restaurant.getEvaluations().addAll(CompleteEvaluationDAO.findByRestaurantNumero(restaurant));



                restaurants.add(restaurant);
            }
            return restaurants;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int insert(Restaurant restaurant) {
        try(Connection cnn = DBOracleDriverManager.getConnection();
            OraclePreparedStatement prepareStatement = (OraclePreparedStatement) cnn.prepareStatement(QUERY_INSERT)) {
            prepareStatement.setString(1, restaurant.getName());
            prepareStatement.setString(2, restaurant.getStreet());
            prepareStatement.setString(3, restaurant.getDescription());
            prepareStatement.setString(4, restaurant.getWebsite());
            prepareStatement.setInt(5, restaurant.getType().getId());
            prepareStatement.setInt(6, restaurant.getAddress().getCity().getId());
            prepareStatement.registerReturnParameter(7, OracleTypes.NUMBER);

            prepareStatement.executeQuery();

            ResultSet rs = prepareStatement.getReturnResultSet();
            while (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(Restaurant restaurant) {
        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement prepareStatement = cnn.prepareStatement("UPDATE RESTAURANTS SET NOM = ?, ADRESSE = ?, DESCRIPTION = ?, SITE_WEB  = ?, FK_TYPE = ?, FK_VILL = ? WHERE NUMERO = ?")) {
            prepareStatement.setString(1, restaurant.getName());
            prepareStatement.setString(2, restaurant.getStreet());
            prepareStatement.setString(3, restaurant.getDescription());
            prepareStatement.setString(4, restaurant.getWebsite());
            prepareStatement.setInt(5, restaurant.getType().getId());
            prepareStatement.setInt(6, restaurant.getAddress().getCity().getId());
            prepareStatement.setInt(7, restaurant.getId());

            prepareStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void remove(Restaurant restaurant) {
        for (Evaluation eval : restaurant.getEvaluations()) {
            GradeDAO.remove(eval.getId());
        }

        CompleteEvaluationDAO.remove(restaurant);
        BasicEvaluationDAO.remove(restaurant);

        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_DELETE)) {
            prepareStatement.setInt(1, restaurant.getId());
            prepareStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
