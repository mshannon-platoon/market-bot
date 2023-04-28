package com.marketbot.marketbot;

import com.marketbot.marketbot.market.services.BasicMarketGenerationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketBotApplication.class, args);
	}

}
