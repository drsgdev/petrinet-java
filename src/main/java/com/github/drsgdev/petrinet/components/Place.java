package com.github.drsgdev.petrinet.components;

import java.util.Objects;

import com.github.drsgdev.petrinet.util.NamedObject;

import lombok.Getter;
import lombok.Setter;

public class Place extends NamedObject {

  @Getter
  @Setter
  private int tokens = 0;

  @Getter
  @Setter
  private int maxTokens = INFINITE_TOKENS;

  public Place(String name) {
    super(name);
  }

  public boolean filled() {
    return tokens == maxTokens;
  }

  public boolean empty() {
    return tokens == 0;
  }

  public boolean sufficientTokens(int threshold) {
    return tokens >= threshold;
  }

  public boolean sufficientSpace(int tokens) {
    return hasInfiniteTreshold() || this.tokens + tokens <= maxTokens;
  }

  public boolean hasInfiniteTreshold() {
    return maxTokens == INFINITE_TOKENS;
  }

  public void addTokens(int tokens) {
    if (sufficientSpace(tokens)) {
      this.tokens += tokens;
    }
  }

  public void removeTokens(int tokens) {
    if (!empty()) {
      this.tokens -= tokens;
    }
  }

  @Override
  public String toString() {
    return name + " tokens: " + this.tokens + " treshold: " + (hasInfiniteTreshold() ? "unlimited" : this.maxTokens);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxTokens, tokens);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Place other = (Place) obj;
    return maxTokens == other.maxTokens && tokens == other.tokens;
  }
}
