package com.fyers.api.utility;

import java.util.List;
import java.util.ArrayList;

public class RSIIndicators {

    // Function to calculate RSI for a list of closing prices
    public static List<Double> calculateRSI(List<Double> prices, int period) {
        List<Double> rsiValues = new ArrayList<>();

        if (prices == null || prices.size() < period) {
            throw new IllegalArgumentException("Not enough data to calculate RSI");
        }

        // Calculate the initial gains and losses for the first 'period' days
        double gainSum = 0;
        double lossSum = 0;
        
        // Calculate gains and losses for the first period
        for (int i = 1; i <= period; i++) {
            double change = prices.get(i) - prices.get(i - 1);
            if (change > 0) {
                gainSum += change;
            } else {
                lossSum -= change;  // Loss is always positive
            }
        }

        // Calculate the initial average gain and loss
        double avgGain = gainSum / period;
        double avgLoss = lossSum / period;

        // Calculate the first RSI
        double rs = (avgLoss == 0) ? 100 : avgGain / avgLoss;
        double rsi = 100 - (100 / (1 + rs));
        rsiValues.add(rsi);

        // Calculate RSI for subsequent periods
        for (int i = period + 1; i < prices.size(); i++) {
            double change = prices.get(i) - prices.get(i - 1);

            double gain = 0;
            double loss = 0;

            if (change > 0) {
                gain = change;
            } else {
                loss = -change;  // loss is always positive
            }

            // Update the average gain and loss using smoothing technique
            avgGain = (avgGain * (period - 1) + gain) / period;
            avgLoss = (avgLoss * (period - 1) + loss) / period;

            // Calculate the RS and RSI
            rs = (avgLoss == 0) ? 100 : avgGain / avgLoss;
            rsi = 100 - (100 / (1 + rs));
            rsiValues.add(rsi);
        }

        return rsiValues;
    }

}

