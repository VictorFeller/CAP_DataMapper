package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityMapper {
    public static List<City> findByNom(Connection cnn, String nom)  {
        try (PreparedStatement prepareStatement = cnn.prepareStatement("SELECT NUMERO, CODE_POSTAL, NOM_VILLE FROM VILLES WHERE NOM_VILLE = ?")){
            prepareStatement.setString(1, nom);
            ResultSet resultSet = prepareStatement.executeQuery();

            List<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                cities.add(new City(resultSet.getInt("NUMERO"), resultSet.getString("CODE_POSTAL"), resultSet.getString("NOM_VILLE")));
            }

             return cities;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insert(Connection cnn, City city) {
        try(PreparedStatement prepareStatement = cnn.prepareStatement("INSERT INTO VILLES (CODE_POSTAL, NOM_VILLE) VALUES (?,?)")) {
            prepareStatement.setString(1, city.getZipCode());
            prepareStatement.setString(2, city.getCityName());
            prepareStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
