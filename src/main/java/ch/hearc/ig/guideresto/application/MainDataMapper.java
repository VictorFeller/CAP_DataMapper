package ch.hearc.ig.guideresto.application;

public class MainDataMapper {






    /*//Test insert City
    Connection cnn = DBOracleDriverManager.getConnection();

    CityMapper cityMapper = new CityMapper();
    City city = new City(null, "2336", "Les Bois");
    cityMapper.insert(cnn, city);
    System.out.println(cityMapper.findByNom(cnn, "Le Noirmont"));

    cnn.commit();

    //Close connection
    DBOracleDriverManager.closeConnection();*/


//    //Test select restaurant
//    Connection cnn = DBOracleDriverManager.getConnection();
//
//    RestaurantMapper restaurantMapper = new RestaurantMapper();
//
//    //Select du restaurant
////    System.out.println(restaurantMapper.findByNom(cnn, "Fleur-de-Lys"));
//
//    Restaurant restaurant = new Restaurant(null,
//            "Wenger",
//            "Resto gastro",
//            "https://wenger.ch",
//            "Rue de la gare 10",
//            CityMapper.findByNom(cnn, "Le Noirmont").get(0),
//            RestaurantTypeMapper.findByNom(cnn, "Restaurant gastronomique").get(0));
//
//    //Insertion du restaurant
//    restaurantMapper.insert(cnn, restaurant);
//
//    cnn.commit();
//
//    //Close connection
//    DBOracleDriverManager.closeConnection();
}
