package com.github.drsgdev.gui.simple;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

import com.github.drsgdev.petrinet.Petrinet;
import com.github.drsgdev.petrinet.Petrinet.Direction;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleGUI {

  @NonNull
  private Petrinet network;

  private Console console = System.console();
  private Scanner inScanner = new Scanner(System.in);

  public void draw() {
    firstPage();
  }

  private void firstPage() {
    cover();

    System.out.println("1. Build network");
    System.out.println("2. Exit");

    input(() -> {
      switch (inScanner.nextInt()) {
        case 1:
          builder();
          break;
        case 2:
          System.exit(0);
          break;
        default:
          err();
          break;
      }

      firstPage();
    });
  }

  private void builder() {
    cover();

    System.out.println("1. Start");
    System.out.println("2. Add connection");
    System.out.println("3. Add tokens");
    System.out.println("4. Set tickrate");
    System.out.println("5. Destroy");
    System.out.println("6. Back");

    input(() -> {
      switch (inScanner.nextInt()) {
        case 1:
          networkInfo();
          break;
        case 2:
          handleConnection();
          waitForInput();
          break;
        case 3:
          handleTokens();
          waitForInput();
          break;
        case 4:
          handleTickrate();
          break;
        case 5:
          network.destroy();
          firstPage();
          break;
        case 6:
          firstPage();
          break;
        default:
          err();
          break;
      }

      builder();
    });
  }

  private void handleConnection() {
    cover();

    String placeName = handleInput("Input place name: ");
    String directionName = handleInput("Input direction(from / to <default>): ");
    String transitionName = handleInput("Input transition name: ");

    Direction direction;
    switch (directionName) {
      case "to":
        direction = Direction.TO;
        break;
      case "from":
        direction = Direction.FROM;
        break;
      default:
        direction = Direction.FROM;
        break;
    }

    network.addConnection(placeName, direction, transitionName);

    cover();
    System.out.println("Added connection: " + placeName + " " + direction + " " + transitionName);
  }

  private void handleTokens() {
    cover();

    String placeName = handleInput("Input place name: ");
    int amnt = Integer.parseInt(handleInput("Input amount: "));

    network.addTokens(placeName, amnt);
  }

  private void handleTickrate() {
    cover();

    long tickrate = Long.parseLong(handleInput("Input desired tickrate(in milliseconds): "));

    network.setTickrate(tickrate);
  }

  private void networkInfo() {
    if (network.isBuilt()) {
      if (network.isStarted()) {
        network.resume();
      } else {
        network.start();
      }

      printInfo();
    } else {
      System.out.println("Build the network first!");
      waitForInput();
    }
  }

  class InfoPrinter extends Thread {
    private boolean enabled = true;

    public synchronized void disable() {
      enabled = false;
    }

    public void run() {
      while (enabled) {
        cover();

        network.info();
      }
    }
  }

  private void printInfo() {
    InfoPrinter info = new InfoPrinter();

    info.start();
    console.readLine();
    info.disable();
  }

  private void cover() {
    clrscr();

    System.out.println("Petrinet builder");
    System.out.println("");
  }

  public static void clrscr() {
    try {
      if (System.getProperty("os.name").contains("Windows"))
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      else
        new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
    } catch (IOException | InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  private void err() {
    System.out.println("Input is not recognized!");
    waitForInput();
  }

  private void waitForInput() {
    System.out.println("Press enter to continue...");
    console.readLine();
  }

  private void input(Runnable func) {
    System.out.println("");
    System.out.print("Input command: ");

    while (true) {
      if (inScanner.hasNextInt()) {
        func.run();
      } else {
        err();
      }
    }
  }

  private String handleInput(String req) {
    while (true) {
      System.out.println(req);
      String line = console.readLine();
      if (!line.isEmpty()) {
        return line;
      } else {
        err();
      }
    }
  }
}
