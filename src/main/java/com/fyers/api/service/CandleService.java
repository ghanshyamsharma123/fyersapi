package com.fyers.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fyers.api.dao.CandleRepository;
import com.fyers.api.entity.Candle;

@Service
public class CandleService {
	@Autowired
	private CandleRepository candleRepository;

	public Candle createCandle(Candle candle) {
		return candleRepository.save(candle);
	}

	public List<Candle> createCandles(List<Candle> candles) {
		return candleRepository.saveAll(candles);
	}

	public Candle getCandleById(int id) {
		return candleRepository.findById(id).orElse(null);
	}

	public List<Candle> getCandles() {
		return candleRepository.findAll();
	}
	
	public String deleteCandleById(int id) {
		candleRepository.deleteById(id);
		return "Candle got deleted";
	}

	public CandleRepository getCandleRepository() {
		return candleRepository;
	}

	public void setCandleRepository(CandleRepository candleRepository) {
		this.candleRepository = candleRepository;
	}

}
