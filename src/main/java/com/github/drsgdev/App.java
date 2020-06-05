package com.github.drsgdev;

import com.github.drsgdev.petrinet.Petrinet;
import com.github.drsgdev.petrinet.Petrinet.Direction;

public abstract class App {

  public static void main(String[] args) throws InterruptedException {
    Petrinet network = new Petrinet();

    network.addConnection("p1", "t1", Direction.FROM_PLACE);
    network.addConnection("p1", "t1", Direction.TO_PLACE);

    network.addConnection("p2", "t1", Direction.TO_PLACE);

    network.setTickrate(1000);

    network.start();

    System.console().readLine();
    network.addTokens("p1", 1);

    while (true) {
      network.info();
      System.out.println();

      Thread.sleep(1000);
    }
  }
}