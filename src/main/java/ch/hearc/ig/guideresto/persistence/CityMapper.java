package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CityMapper {
    private static final String QUERY_ALL = "SELECT NUMERO, CODE_POSTAL, NOM_VILLE FROM VILLES";
    public static final String QUERY_BY_NOM = "SELECT NUMERO, CODE_POSTAL, NOM_VILLE FROM VILLES WHERE NUMERO = ?";

    public static City findByNumero(int cityNumero)  {
        try (Connection cnn = DBOracleDriverManager.getConnection();
             PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_BY_NOM)){
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

    public static Set<City> findAll() {
        try(Connection cnn = DBOracleDriverManager.getConnection();
            PreparedStatement prepareStatement = cnn.prepareStatement(QUERY_ALL)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            Set<City> cities = new HashSet<>();
            while(resultSet.next()) {
                cities.add(new City(
                        resultSet.getInt("NUMERO"),
                        resultSet.getString("CODE_POSTAL"),
                        resultSet.getString("NOM_VILLE")));

            }
            return cities;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insert(City city) {
        try(Connection cnn = DBOracleDriverManager.getConnection();
                PreparedStatement prepareStatement = cnn.prepareStatement("INSERT INTO VILLES (CODE_POSTAL, NOM_VILLE) VALUES (?,?)")) {
            prepareStatement.setString(1, city.getZipCode());
            prepareStatement.setString(2, city.getCityName());
            prepareStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
