package com.fyers.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fyers.api.entity.Candle;
import com.fyers.api.service.CandleService;

@RestController
public class CandleController {
	@Autowired
	private CandleService candleService;

	@PostMapping("/addCandle")
	public Candle addUser(@RequestBody Candle candle) {
		return candleService.createCandle(candle);
	}

	@PostMapping("/addCandles")
	public List<Candle> addCandles(@RequestBody List<Candle> candles) {
		return candleService.createCandles(candles);
	}

	@GetMapping("/candle/{id}")
	public Candle getCandleById(@PathVariable int id) {
		return candleService.getCandleById(id);
	}

	@GetMapping("/candles")
	public List<Candle> getAllCandles() {
		return candleService.getCandles();
	}

	@DeleteMapping("/candle/{id}")
	public String deleteCandle(@PathVariable int id) {
		return candleService.deleteCandleById(id);
	}
}
