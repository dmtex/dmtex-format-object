package com.github.dmtex.format.object.spi;

import java.util.List;
import java.util.ResourceBundle;

public class ResourceBundleObjectFormatterServiceProvider implements ObjectFormatterServiceProvider {

  @Override
  public List<ResourceBundle> resourceBundles() {
    return List.of(ResourceBundle.getBundle("test"));
  }
}
