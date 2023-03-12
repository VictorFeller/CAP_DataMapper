package ch.hearc.ig.guideresto.application;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.persistence.CityMapper;
import ch.hearc.ig.guideresto.persistence.DBOracleDriverManager;
import ch.hearc.ig.guideresto.persistence.FakeItems;
import ch.hearc.ig.guideresto.presentation.CLI;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws SQLException {
//    var scanner = new Scanner(System.in);
//    var fakeItems = new FakeItems();
//    var printStream = System.out;
//    var cli = new CLI(scanner, printStream, fakeItems);
//
//    cli.start();


    //Test insert City
    Connection cnn = DBOracleDriverManager.getConnection();

    CityMapper cityMapper = new CityMapper();
    City city = new City(null, "2336", "Les Bois");
    cityMapper.insert(cnn, city);
    System.out.println(cityMapper.findByNom(cnn, "Le Noirmont"));

    cnn.commit();

    //Close connection
    DBOracleDriverManager.closeConnection();
  }
}
