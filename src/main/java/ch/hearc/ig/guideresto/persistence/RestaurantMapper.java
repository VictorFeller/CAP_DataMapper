package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMapper {
    public List<Restaurant> findByNom(Connection cnn, String nom) {
        try (PreparedStatement prepareStatement = cnn.prepareStatement("SELECT NUMERO, NOM, ADRESSE, DESCRIPTION, SITE_WEB FROM RESTAURANTS WHERE NOM = ?")){
            prepareStatement.setString(1, nom);
            ResultSet resultSet = prepareStatement.executeQuery();

            List<Restaurant> restaurants = new ArrayList<>();
            while (resultSet.next()) {
                restaurants.add(new Restaurant(resultSet.getInt("NUMERO"), resultSet.getString("NOM"), resultSet.getString("DESCRIPTION"), null, null, null, null));
            }

             return restaurants;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insert(Connection cnn, Restaurant restaurant){
        try(PreparedStatement prepareStatement = cnn.prepareStatement("INSERT INTO RESTAURANTS (NOM, ADRESSE, DESCRIPTION, SITE_WEB, FK_TYPE, FK_VILL) VALUES (?,?, ?, ?, ?, ?)")) {
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

}
