package ch.hearc.ig.guideresto.business;

import java.time.LocalDate;

public abstract class Evaluation {

  private Integer id;
  private LocalDate visitDate;
  private Restaurant restaurant;

  public Evaluation(Integer id, LocalDate visitDate, Restaurant restaurant) {
    this.id = id;
    this.visitDate = visitDate;
    this.restaurant = restaurant;
  }

  public LocalDate getVisitDate() {
    return visitDate;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}