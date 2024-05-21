package com.yosto.yostobackend.generic;

import java.util.Map;

public class ServiceException extends RuntimeException {
  private final Map<String, String> errors;

  public ServiceException(Map<String, String> errors) {
    super(errors.values().toString());
    this.errors = errors;
  }

  public Map<String, String> getErrors() {
    return errors;
  }
}
