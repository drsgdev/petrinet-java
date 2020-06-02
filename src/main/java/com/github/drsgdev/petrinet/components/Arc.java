package com.github.drsgdev.petrinet.components;

import java.util.Objects;

import com.github.drsgdev.petrinet.util.NamedObject;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Arc extends NamedObject {

  @Getter
  @Setter
  @NonNull
  private Place place;

  @Getter
  @Setter
  private int weight = 1;

  public Arc(String name, Place place) {
    super(name);
    this.place = place;
  }

  public boolean canSend() {
    return place.sufficientTokens(weight);
  }

  public int freeSpace() {
    return place.getMaxTokens() - place.getTokens();
  }

  public boolean canRecieve() {
    return !place.filled();
  }

  public boolean canRecieve(int tokens) {
    return canRecieve() && place.sufficientSpace(tokens);
  }

  public void send() {
    place.removeTokens(weight);
  }

  public void recieve() {
    place.addTokens(weight);
  }

  public void addWeight(int amnt) {
    weight += amnt;
  }

  @Override
  public int hashCode() {
    return Objects.hash(place);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Arc other = (Arc) obj;
    return Objects.equals(place, other.place);
  }
}
