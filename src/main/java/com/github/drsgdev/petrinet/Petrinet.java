package com.github.drsgdev.petrinet;

import java.util.HashMap;
import java.util.Map;

import com.github.drsgdev.petrinet.components.Arc;
import com.github.drsgdev.petrinet.components.Place;
import com.github.drsgdev.petrinet.components.Transition;

import lombok.Getter;

public class Petrinet {

  public enum Direction {
    TO_PLACE, FROM_PLACE
  }

  @Getter
  private Map<String, Transition> transitions = new HashMap<>();

  @Getter
  private Map<String, Place> places = new HashMap<>();

  @Getter
  private int arcsCount = 0;

  public void addConnection(String placeName, String transitionName, Direction d) throws InterruptedException {
    Place place = places.containsKey(placeName) ? places.get(placeName) : new Place(placeName);
    Transition transition = transitions.containsKey(transitionName) ? transitions.get(transitionName)
        : new Transition(transitionName);

    Arc arc = new Arc("arc" + arcsCount, place);
    arcsCount++;

    switch (d) {
      case TO_PLACE:
        transition.addOutgoing(arc);
        break;
      case FROM_PLACE:
        transition.addIncoming(arc);
        break;
    }

    places.put(placeName, place);
    transitions.put(transitionName, transition);
  }

  public void start() {
    transitions.values().parallelStream().forEach((tr) -> {
      tr.start();
    });
  }

  public void resume() {
    transitions.values().parallelStream().forEach((tr) -> {
      tr.enable();
    });
  }

  public void pause() {
    transitions.values().parallelStream().forEach((tr) -> {
      tr.disable();
    });
  }

  public void info() {
    places.values().stream().forEach((p) -> {
      System.out.println(p);
    });
  }

  public void addTokens(String place, int amnt) {
    if (places.containsKey(place)) {
      places.get(place).addTokens(amnt);
    }
  }
}
