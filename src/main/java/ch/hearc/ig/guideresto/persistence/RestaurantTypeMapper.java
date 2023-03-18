package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.RestaurantType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantTypeMapper {
    private static final String QUERY_BY_NUMERO = "SELECT NUMERO, LIBELLE, DESCRIPTION FROM TYPES_GASTRONOMIQUES WHERE NUMERO = ?";

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
}
