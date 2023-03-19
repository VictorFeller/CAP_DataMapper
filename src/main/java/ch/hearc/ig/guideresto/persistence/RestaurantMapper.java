package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class RestaurantMapper {
    private static final String QUERY_ALL = "SELECT NUMERO, NOM, ADRESSE, DESCRIPTION, SITE_WEB, FK_TYPE, FK_VILL FROM RESTAURANTS";
    public static Set<Restaurant> findAll() {
        try(Connection cnn = DBOracleDriverManager.getConnection();
                PreparedStatement statement = cnn.prepareStatement(QUERY_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            Set<Restaurant> restaurants = new HashSet<>();
            while(resultSet.next()) {
                //TODO
                // Comment recupérer le champ evaluations
                restaurants.add(new Restaurant(
                        resultSet.getInt("NUMERO"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getString("site_web"),
                        resultSet.getString("adresse"),
                        CityMapper.findByNumero(resultSet.getInt("FK_VILL")),
                        RestaurantTypeMapper.findByNumero(resultSet.getInt("FK_TYPE"))));
            }
            return restaurants;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insert(Restaurant restaurant) {
        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement prepareStatement = cnn.prepareStatement("INSERT INTO RESTAURANTS (NOM, ADRESSE, DESCRIPTION, SITE_WEB, FK_TYPE, FK_VILL) VALUES (?,?, ?, ?, ?, ?)")) {
            prepareStatement.setString(1, restaurant.getName());
            prepareStatement.setString(2, restaurant.getStreet());
            prepareStatement.setString(3, restaurant.getDescription());
            prepareStatement.setString(4, restaurant.getWebsite());
            prepareStatement.setInt(5, restaurant.getType().getId());
            prepareStatement.setInt(6, restaurant.getAddress().getCity().getId());

            prepareStatement.executeQuery();
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
}
