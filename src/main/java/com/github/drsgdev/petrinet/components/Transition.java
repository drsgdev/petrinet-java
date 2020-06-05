package com.github.drsgdev.petrinet.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

public class Transition extends Thread {

  private boolean enabled = true;

  @Getter
  @Setter
  private long delay = 1l;

  @Getter
  private Map<String, Arc> incoming = new HashMap<>();
  @Getter
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

  public boolean isDisconnected() {
    return incoming.isEmpty() || outgoing.isEmpty();
  }

  public boolean isEnabled() {
    return enabled;
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

      tick();
    }
  }

  private void tick() {
    try {
      Thread.sleep(this.delay);
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
