package com.github.drsgdev.petrinet;

import java.util.HashMap;
import java.util.Map;

import com.github.drsgdev.petrinet.components.Arc;
import com.github.drsgdev.petrinet.components.Place;
import com.github.drsgdev.petrinet.components.Transition;

import lombok.Getter;

public class Petrinet {

  public enum Direction {
    TO, FROM
  }

  @Getter
  private boolean onPause = true;

  @Getter
  private boolean started = false;

  @Getter
  private boolean built = false;

  @Getter
  private Map<String, Transition> transitions = new HashMap<>();

  @Getter
  private Map<String, Place> places = new HashMap<>();

  public void addConnection(String placeName, Direction d, String transitionName) {
    Place place = places.containsKey(placeName) ? places.get(placeName) : new Place(placeName);
    Transition transition = transitions.containsKey(transitionName) ? transitions.get(transitionName)
        : new Transition(transitionName);

    StringBuilder arcName = new StringBuilder(placeName);
    switch (d) {
      case FROM:
        arcName.append("_from_");
        break;
      case TO:
        arcName.append("_to_");
        break;
    }
    arcName.append(transitionName);

    Arc arc = new Arc(arcName.toString(), place);

    switch (d) {
      case FROM:
        transition.addOutgoing(arc);
        break;
      case TO:
        transition.addIncoming(arc);
        break;
    }

    places.put(placeName, place);
    transitions.put(transitionName, transition);
    built = true;
  }

  public void start() {
    if (!started()) {
      transitions.values().parallelStream().forEach((tr) -> {
        tr.start();
      });

      started = true;
    }
  }

  public void resume() {
    if (onPause) {
      transitions.values().parallelStream().forEach((tr) -> {
        tr.enable();
      });

      onPause = false;
    }
  }

  public void pause() {
    if (!onPause) {
      transitions.values().parallelStream().forEach((tr) -> {
        if (tr.isEnabled()) {
          tr.disable();
        }
      });

      onPause = true;
    }
  }

  public void info() {
    places.values().stream().forEach((p) -> {
      System.out.println(p);
    });
  }

  public void setTickrate(final long time) {
    if (time >= 0l) {
      pause();
      transitions.values().parallelStream().forEach((tr) -> {
        tr.setDelay(time);
      });
      resume();
    }
  }

  public void destroy() {
    transitions.clear();
    places.clear();
    onPause = true;
    started = false;
    built = false;
  }

  public void addTokens(String place, int amnt) {
    if (places.containsKey(place)) {
      places.get(place).addTokens(amnt);
    }
  }

  private boolean started() {
    if (started) {
      throw new IllegalStateException();
    } else {
      return false;
    }
  }
}
