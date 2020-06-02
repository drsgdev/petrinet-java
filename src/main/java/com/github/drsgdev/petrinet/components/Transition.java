package com.github.drsgdev.petrinet.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Transition extends Thread {

  private boolean enabled = true;

  private Map<String, Arc> incoming = new HashMap<>();
  private Map<String, Arc> outgoing = new HashMap<>();

  public Transition(String name) {
    super(name);
  }

  public boolean canFire() {
    return incoming.values().parallelStream().allMatch((arc) -> {
      return arc.canSend();
    });
  }

  public void fire() {
    for (Arc arc : incoming.values()) {
      arc.send();
    }

    for (Arc arc : outgoing.values()) {
      arc.recieve();
    }
  }

  public void addIncoming(Arc arc) {
    if (incoming.containsKey(arc.getName())) {
      incoming.get(arc.getName()).addWeight(1);
    } else {
      incoming.put(arc.getName(), arc);
    }
  }

  public void addOutgoing(Arc arc) {
    if (outgoing.containsKey(arc.getName())) {
      outgoing.get(arc.getName()).addWeight(1);
    } else {
      outgoing.put(arc.getName(), arc);
    }
  }

  public boolean disconnected() {
    return incoming.isEmpty() || outgoing.isEmpty();
  }

  public void enable() {
    enabled = true;
  }

  public void disable() {
    enabled = false;
  }

  @Override
  public void run() {
    while (true) {
      if (enabled) {
        if (canFire()) {
          fire();
        }
      }

      delay(500);
    }
  }

  private void delay(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(incoming, outgoing);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Transition other = (Transition) obj;
    return Objects.equals(incoming, other.incoming) && Objects.equals(outgoing, other.outgoing);
  }
}
