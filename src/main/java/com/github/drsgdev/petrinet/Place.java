package com.github.drsgdev.petrinet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
public class Place extends NamedObject {

  @Getter
  @Setter
  private int tokens = 0;

  @Getter
  @Setter
  private int maxTokens = INFINITE_TOKENS;

  public boolean sufficientTokens(int threshold) {
    return tokens >= threshold;
  }

  public boolean hasInfiniteTreshold() {
    return maxTokens == INFINITE_TOKENS;
  }

  public void addTokens(int weight) {
    this.tokens += weight;
  }

  public void removeTokens(int weight) {
    this.tokens -= weight;
  }

  @Override
  public String toString() {
    return name + " tokens: " + this.tokens + " treshold: " + (hasInfiniteTreshold() ? "unlimited" : this.maxTokens);
  }
}
