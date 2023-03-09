package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityMapper {
    private static Connection cnn = null;

    public void openConnection() throws SQLException {
        cnn = new DBOracleDriverManager().getConnection();
    }

    public void closeConnection() {
        try {
            cnn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<City> findByNom(String nom) throws SQLException {
        openConnection();
        try (PreparedStatement prepareStatement = cnn.prepareStatement("SELECT NUMERO, CODE_POSTAL, NOM_VILLE FROM VILLES");
                ResultSet resultSet = prepareStatement.executeQuery()) {
            List<City> cities = new ArrayList<>();
            while (resultSet.next()) {
                cities.add(new City(resultSet.getInt("NUMERO"), resultSet.getString("CODE_POSTAL"), resultSet.getString("NOM_VILLE")));
            }
            return cities;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
