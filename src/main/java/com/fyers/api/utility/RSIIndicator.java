package com.fyers.api.utility;

import java.util.List;


public class RSIIndicator {

    /**
     * Calculates the RSI (Relative Strength Index).
     * @param prices: List of closing prices.
     * @param period: period for calculating RSI.
     * @return RSI value.
     */
    public float calculateRSI(List<Float> prices, int period) {
        if (prices == null || prices.size() <= period) {
            throw new IllegalArgumentException("Insufficient data for the given period.");
        }

        float gain = 0, loss = 0;

        // Calculate initial average gain and loss
        for (int i = 1; i <= period; i++) {
            float change = prices.get(i) - prices.get(i - 1);
            if (change > 0) {
                gain += change;
            } else {
                loss -= change; // Loss is positive
            }
        }
        
        gain /= period;
        loss /= period;

        // Calculate RS value and Avoid division by zero
        float rs =  gain / (loss == 0 ? 1 :loss);

        return 100 - (100 / (1 + rs));
    }
}