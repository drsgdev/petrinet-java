package com.github.drsgdev.petrinet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Arc extends NamedObject {

  @Getter
  @Setter
  private int weight = 1;

  public Arc(String name) {
    super(name);
  }
}
