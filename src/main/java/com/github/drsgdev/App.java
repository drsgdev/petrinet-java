package com.github.drsgdev;

import javax.swing.plaf.basic.BasicTreeUI.KeyHandler;

import com.github.drsgdev.petrinet.Petrinet;
import com.github.drsgdev.petrinet.Petrinet.Direction;

public abstract class App {

  public static void main(String[] args) throws InterruptedException {
    Petrinet network = new Petrinet();

    network.addConnection("p1", "t1", Direction.FROM_PLACE);
    network.addConnection("p1", "t2", Direction.FROM_PLACE);
    network.addConnection("p1", "t3", Direction.FROM_PLACE);
    network.addConnection("p1", "t4", Direction.FROM_PLACE);

    network.addConnection("p2", "t1", Direction.TO_PLACE);
    network.addConnection("p3", "t2", Direction.TO_PLACE);
    network.addConnection("p4", "t3", Direction.TO_PLACE);
    network.addConnection("p5", "t4", Direction.TO_PLACE);

    network.start();

    System.console().readLine();
    network.addTokens("p1", 100);

    while (true) {
      network.info();
      System.out.println();

      Thread.sleep(1000);
    }
  }
}