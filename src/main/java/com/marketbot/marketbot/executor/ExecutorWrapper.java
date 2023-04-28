package com.marketbot.marketbot.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Component;

@Component
public class ExecutorWrapper {

  private ExecutorService executor = Executors.newFixedThreadPool(2);

  public void submit(Runnable runnable){
    executor.submit(runnable);
  }

}
