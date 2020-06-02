package com.github.drsgdev.petrinet;

import java.util.ArrayList;
import java.util.List;

public class Transition extends NamedObject {

  private List<Arc> incoming = new ArrayList<Arc>();
  private List<Arc> outgoing = new ArrayList<Arc>();

  protected Transition(String name) {
    super(name);
  }

  public boolean canFire() {
    return true;
  }

  public void fire() {
  }

  public void addIncoming(Arc arc) {
    this.incoming.add(arc);
  }

  public void addOutgoing(Arc arc) {
    this.outgoing.add(arc);
  }

  public boolean disconnected() {
    return incoming.isEmpty() && outgoing.isEmpty();
  }

  @Override
  public String toString() {
    return name + (disconnected() ? " disconnected!" : (canFire() ? " ready to fire!" : ""));
  }

}
