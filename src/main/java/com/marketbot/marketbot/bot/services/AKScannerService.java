package com.marketbot.marketbot.bot.services;

import com.marketbot.marketbot.bot.models.ScanCriteria;
import com.marketbot.marketbot.executor.ExecutorWrapper;
import com.marketbot.marketbot.market.models.MarketplaceItem;
import com.marketbot.marketbot.market.models.SimulatedMarketplace;
import com.marketbot.marketbot.market.models.Weapon;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AKScannerService implements ScannerService, Runnable {

  private final SimulatedMarketplace simulatedMarketplace;
  private final ExecutorWrapper executorWrapper;

  private final ScanCriteria scanCriteria = ScanCriteria
      .builder()
      .name(Weapon.AK47_BRIGHT.name())
      .priceLessThan(33).build();

  private final Set<String> uniqueUUIDs = new HashSet<>();

  public AKScannerService(SimulatedMarketplace simulatedMarketplace,
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
    scan();
  }

  @Override
  public List<MarketplaceItem> scan() {
    for (int i = 0; i < 60; i++) {
      log.info("AKScannerService - Scanning. . .");
      var uuidToItemMap = simulatedMarketplace.getListingsForItem(scanCriteria.getName());
      if (uuidToItemMap != null && !uuidToItemMap.isEmpty()) {
        uuidToItemMap.forEach(value -> {
          if (value.getCost() <= scanCriteria.priceLessThan && !uniqueUUIDs.contains(value.getUniqueIdentifier().toString())) {
            log.info("AKScannerService - NEW AK47_BRIGHT ITEM FOR BUYING: {}", value);
            uniqueUUIDs.add(value.getUniqueIdentifier().toString());
          }
        });
      }

      try {
        log.info("AKScannerService - Scanning. . . Finished...");
        Thread.sleep(5000);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
