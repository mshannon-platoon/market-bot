package com.marketbot.marketbot.market.services;

import com.marketbot.marketbot.executor.ExecutorWrapper;
import com.marketbot.marketbot.market.models.MarketplaceItem;
import com.marketbot.marketbot.market.models.SimulatedMarketplace;
import com.marketbot.marketbot.market.models.Weapon;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The basic market generation service will generate 3 different market items. Which will be 3 types
 * of weapon skins.
 * <p>
 * We have the AK47 - BRIGHT We have the M4A3 - CYRUS We have the AWP - DRAGON
 * <p>
 * author: Max Shannon Date: 09/13/2022
 */
@Service
@Slf4j
public class BasicMarketGenerationService implements GenerationService, Runnable  {

  private final SimulatedMarketplace simulatedMarketplace;
  private final Random random = new Random();
  private final ExecutorWrapper executorWrapper;

  private final Map<Integer, String> weaponMap =
      Map.of(1, Weapon.AK47_BRIGHT.name(),
          2, Weapon.M4A3_CYRUS.name(),
          3, Weapon.AWP_DRAGON.name());

  public BasicMarketGenerationService(SimulatedMarketplace simulatedMarketplace,
      ExecutorWrapper executorWrapper) {
    this.simulatedMarketplace = simulatedMarketplace;
    this.executorWrapper = executorWrapper;
  }

  @PostConstruct
  public void init() {
    executorWrapper.submit(this);
  }

  @Override
  public void run() {
    try {
      simulateMarket();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * In this method we want to simulate a market. How do we do that?
   * <p>
   * We want to generate a random amount of random weapons, with strict Titles - but we want to
   * generate their value between 0.01 - 0.99c. We also are going to run this in a while(true) loop
   * but sleep every cycle for 30 seconds.
   * <p>
   * //TODO: Make this 30 seconds configurable. We also might be able to tune it to be really
   * configurable.
   */
  @Override
  public void simulateMarket() throws InterruptedException {
    log.info("BasicMarketGenerationService simulateMarket - Starting...");
    for (int i = 0; i < 20; i++) {

      generateRandomNumberOfMarketplaceItems(randomBetween(1, 100));

      Thread.sleep(30000);
    }
  }

  private void generateRandomNumberOfMarketplaceItems(int howManyItemsToGenerate) {
    for (int i = 0; i < howManyItemsToGenerate; i++) {
      simulatedMarketplace.listItem(generateItem());
    }
    log.info("BasicMarketGenerationService simulateMarket - generatedItems.size: {}. Finished.",
        howManyItemsToGenerate);
  }

  private MarketplaceItem generateItem() {
    return MarketplaceItem
        .builder()
        .title(weaponMap.get(randomBetween(1, 3)))
        .cost(randomBetween(1, 99))
        .uniqueIdentifier(UUID.randomUUID())
        .timeToLiveInSeconds(randomBetween(60, 120))
        .build();
  }

  public double randomBetween(double max, double min) {
    return min + (max - min) * random.nextDouble();
  }

  public int randomBetween(int min, int max) {
    return random.nextInt(max - min + 1) + min;
  }
}
