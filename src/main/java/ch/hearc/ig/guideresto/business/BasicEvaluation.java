package ch.hearc.ig.guideresto.business;

import java.time.LocalDate;

//Correspond à la table LIKES
public class BasicEvaluation extends Evaluation {

  private boolean likeRestaurant;
  private String ipAddress;

  public BasicEvaluation(Integer id, LocalDate visitDate, Restaurant restaurant, boolean likeRestaurant,
      String ipAddress) {
    super(id, visitDate, restaurant);
    this.likeRestaurant = likeRestaurant;
    this.ipAddress = ipAddress;
  }

  public Boolean isLikeRestaurant() {
    return likeRestaurant;
  }

  public String getIpAddress() {
    return ipAddress;
  }
}