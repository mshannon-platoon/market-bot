package com.marketbot.marketbot.market.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
public class SimulatedMarketplace {

  /*
  * A Map of MarketplaceItem.title to Map of MarketPlaceItem.UUID to MarketplaceItem.
  *
  * */
  private final Map<String, List<MarketplaceItem>> market = new HashMap<>();

  public void listItem(MarketplaceItem itemToList) {
    //TODO: Clean up this code, was rappid prototype
    var marketPlaceItems = market.getOrDefault(itemToList.getTitle(), new ArrayList<>());
    marketPlaceItems.add(itemToList);
    market.putIfAbsent(itemToList.getTitle(), marketPlaceItems);

    log.debug("successfully added marketplace-item to the market. marketplaceItem: {}", itemToList);
  }

  public void unList(String itemTitle, UUID itemUUID) {
    var UUIDMap = market.get(itemTitle);
    if (UUIDMap != null) {
      var marketplaceItem = UUIDMap.remove(itemUUID);
      log.info("successfully removed marketplace-item to the market. marketplaceItem: {}", marketplaceItem);
    }
  }

  public List<MarketplaceItem> getListingsForItem(String itemName){
    return market.get(itemName);
  }

}
