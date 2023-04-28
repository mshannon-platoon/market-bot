package com.marketbot.marketbot.market.models;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class MarketplaceItem {

  private Integer cost;
  private String title;
  private UUID uniqueIdentifier;
  private Integer timeToLiveInSeconds;

}
