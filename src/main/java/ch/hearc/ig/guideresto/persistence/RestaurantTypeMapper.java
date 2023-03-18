package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.RestaurantType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class RestaurantTypeMapper {
    private static final String QUERY_BY_NUMERO = "SELECT NUMERO, LIBELLE, DESCRIPTION FROM TYPES_GASTRONOMIQUES WHERE NUMERO = ?";
    private static final String QUERY_ALL = "SELECT NUMERO, LIBELLE, DESCRIPTION FROM TYPES_GASTRONOMIQUES";

    public static RestaurantType findByNumero(int restaurantTypeNumero) {
        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_BY_NUMERO)) {
            prepareStatement.setInt(1, restaurantTypeNumero);
            ResultSet resultSet = prepareStatement.executeQuery();
            RestaurantType restaurantType = null;
            while(resultSet.next()) {
                restaurantType = new RestaurantType(resultSet.getInt("NUMERO"), resultSet.getString("LIBELLE"), resultSet.getString("DESCRIPTION"));
            }
            return restaurantType;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<RestaurantType> findAll() {
        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_ALL)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            Set<RestaurantType> restaurantTypes = new HashSet<>();
            while(resultSet.next()) {
                restaurantTypes.add(new RestaurantType(
                        resultSet.getInt("NUMERO"),
                        resultSet.getString("LIBELLE"),
                        resultSet.getString("DESCRIPTION")));

            }
            return restaurantTypes;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
