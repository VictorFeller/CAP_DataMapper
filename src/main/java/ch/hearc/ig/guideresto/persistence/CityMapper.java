package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityMapper {
    public static City findByNumero(int cityNumero)  {
        try (Connection cnn = DBOracleDriverManager.getConnection();
             PreparedStatement prepareStatement = cnn.prepareStatement("SELECT NUMERO, CODE_POSTAL, NOM_VILLE FROM VILLES WHERE NUMERO = ?")){
            prepareStatement.setInt(1, cityNumero);
            ResultSet resultSet = prepareStatement.executeQuery();
            City city = null;
            while (resultSet.next()) {
                city = new City(resultSet.getInt("NUMERO"), resultSet.getString("CODE_POSTAL"), resultSet.getString("NOM_VILLE"));
            }

             return city;
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
