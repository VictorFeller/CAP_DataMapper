package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.RestaurantType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantTypeMapper {

    public static List<RestaurantType> findByNom(Connection cnn, String libelle) {
        try (PreparedStatement prepareStatement = cnn.prepareStatement("SELECT NUMERO, LIBELLE, DESCRIPTION FROM TYPES_GASTRONOMIQUES WHERE LIBELLE = ?")){
            prepareStatement.setString(1, libelle);
            ResultSet resultSet = prepareStatement.executeQuery();

            List<RestaurantType> restaurantTypes = new ArrayList<>();
            while (resultSet.next()) {
                restaurantTypes.add(new RestaurantType(resultSet.getInt("NUMERO"), resultSet.getString("LIBELLE"), resultSet.getString("DESCRIPTION")));
            }

            return restaurantTypes;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
