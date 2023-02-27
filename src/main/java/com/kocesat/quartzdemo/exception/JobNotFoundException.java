package com.kocesat.quartzdemo.exception;

public class JobNotFoundException extends AppRuntimeException{
  public JobNotFoundException() {
    super("Job not found");
  }

  public JobNotFoundException(String message) {
    super(message);
  }
}
