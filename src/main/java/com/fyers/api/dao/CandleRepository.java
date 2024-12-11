package com.fyers.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fyers.api.entity.Candle;

public interface CandleRepository extends JpaRepository<Candle, Integer> {

}
