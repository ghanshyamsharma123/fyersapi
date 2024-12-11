package com.fyers.api;

import java.io.IOException;

import com.fyers.api.python.FyersTokenProcessBuilder;

public class FyersApiTest {

	public static void main(String[] args) {
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
			fyers.getHistoryData("NSE:SBIN-EQ", "1D", "1", "2024-01-01", "2024-11-02", "1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
