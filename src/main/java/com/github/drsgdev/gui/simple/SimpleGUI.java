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
        Runtime.getRuntime().exec("clear");
    } catch (IOException | InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  private void err() {
    System.out.println("Input is not recognized!");
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
        waitForInput();
      }
    }
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
          waitForInput();
          break;
      }
    });
  }

  private void builder() {
    cover();

    System.out.println("1. Start");
    System.out.println("2. Add connection");
    System.out.println("3. Add tokens");
    System.out.println("4. Destroy");
    System.out.println("5. Back");

    input(() -> {
      switch (inScanner.nextInt()) {
        case 1:
          networkInfo();
          break;
        case 2:
          handleConnection();
          waitForInput();
          builder();
          break;
        case 3:
          handleTokens();
          waitForInput();
          builder();
          break;
        case 4:
          network.destroy();
          firstPage();
          break;
        case 5:
          firstPage();
          break;
        default:
          err();
          waitForInput();
          builder();
          break;
      }
    });
  }

  private void handleConnection() {
    cover();

    String placeName = handleInput("Input place name: ");
    String transitionName = handleInput("Input transition name: ");
    String directionName = handleInput("Input direction(from <default> / to): ");

    Direction direction;
    switch (directionName) {
      case "from":
        direction = Direction.FROM_PLACE;
        break;
      case "to":
        direction = Direction.TO_PLACE;
        break;
      default:
        direction = Direction.FROM_PLACE;
        break;
    }

    network.addConnection(placeName, transitionName, direction);
    System.out.println("added " + placeName + " " + direction + " " + transitionName);
  }

  private void handleTokens() {
    cover();

    String placeName = handleInput("Input place name: ");
    int amnt = Integer.parseInt(handleInput("Input amount: "));

    network.addTokens(placeName, amnt);
  }

  private void networkInfo() {
    if (network.isBuilt()) {
      network.setTickrate(100);

      if (network.isStarted()) {
        network.resume();
      } else {
        network.start();
      }

      while (true) {
        cover();

        network.info();
        if (Thread.currentThread().isInterrupted()) {
          network.pause();
        }
      }
    } else {
      System.out.println("Build the network first!");
      waitForInput();
    }

    builder();
  }

  private String handleInput(String req) {
    while (true) {
      System.out.println(req);
      if (inScanner.hasNextLine()) {
        String line = inScanner.nextLine();
        if (!line.isEmpty()) {
          return line;
        } else {
          err();
          waitForInput();
        }
      }
    }
  }
}
