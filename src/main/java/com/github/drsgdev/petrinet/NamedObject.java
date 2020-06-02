package com.github.drsgdev.petrinet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public abstract class NamedObject {

  @Getter
  @Setter
  protected String name;

  public static final int INFINITE_TOKENS = -1;
}
