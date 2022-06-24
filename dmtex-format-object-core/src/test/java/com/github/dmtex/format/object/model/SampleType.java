package com.github.dmtex.format.object.model;

import com.github.dmtex.format.object.KeyAware;

public enum SampleType implements KeyAware {

  NAME("name"),
  UNKNOWN("unknown");

  private final String key;

  SampleType(String key) {
    this.key = key;
  }

  @Override
  public String getKey() {
    return key;
  }
}
