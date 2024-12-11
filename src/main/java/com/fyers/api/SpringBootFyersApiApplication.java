package com.fyers.api;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.fyers.api.dao.CandleRepository;
import com.fyers.api.entity.Candle;
import com.fyers.api.python.FyersTokenProcessBuilder;
import com.fyers.api.utility.RSIIndicator;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan({"com.fyers.api"})
public class SpringBootFyersApiApplication {

	@Autowired
	private CandleRepository candleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootFyersApiApplication.class, args);
		
	}
	
	@PostConstruct
	public void processHistoryData() {
		
		String token = null;
		FyersTokenProcessBuilder pv = new FyersTokenProcessBuilder();
		try {
			token = pv.getToken();
			System.out.println("Access Token: "+ token);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//FyersHistoryDataAccess fyers = new FyersHistoryDataAccess("XEFOEHY3RT-100", "YLV6O113EU");
		FyersHistoryData fyers = new FyersHistoryData("XEFOEHY3RT-100", "YLV6O113EU");
		if(token != null)
			fyers.setAccessToken(token);
		try {
			List<Candle> candles = fyers.getHistoryData("NSE:SBIN-EQ", "1D", "1", "2024-01-01", "2024-11-02", "1");
			candleRepository.saveAll(candles);
			List<Candle> candlesValues = candleRepository.findAll();
			
			List<Float> closePriceList = candlesValues.stream().map(Candle::getClose).collect(Collectors.toList());
			RSIIndicator rsi = new RSIIndicator();
			double rsiValue = rsi.calculateRSI(closePriceList, closePriceList.size()-1); 
			System.out.println("RSI Value  : "+rsiValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
