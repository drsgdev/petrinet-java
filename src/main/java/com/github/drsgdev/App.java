package com.github.drsgdev;

import com.github.drsgdev.gui.simple.SimpleGUI;
import com.github.drsgdev.petrinet.Petrinet;

public abstract class App {

  public static void main(String[] args) throws InterruptedException {
    SimpleGUI gui = new SimpleGUI(new Petrinet());

    gui.draw();
  }
}