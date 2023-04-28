package com.marketbot.marketbot.bot.services;

import com.marketbot.marketbot.bot.models.ScanCriteria;
import com.marketbot.marketbot.market.models.MarketplaceItem;
import java.util.List;

public interface ScannerService {

  List<MarketplaceItem> scan();
}
