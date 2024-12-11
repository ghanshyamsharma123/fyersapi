package com.fyers.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.fyers.api.entity.Candle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fyers.api.exception.CommandException;

public class FyersHistoryDataAccess {
	
	private String APPID = ""; 
	private String SECRET = "";
	private static final String HISTORICAL_URL1 = "https://api-t1.fyers.in/data/history?symbol=%s&resolution=%s&date_format=%s&range_from=%s&range_to=%s&cont_flag=%s";
	private String accessToken = null;

    public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	private static final Logger logger = Logger.getLogger(FyersHistoryDataAccess.class.getName());
    
    public FyersHistoryDataAccess(String appid, String secret) {
		this.APPID = appid;
		this.SECRET = secret;
	}

    public void getHistoryData(String instrument, String candlePeriod, String dateFormat, String startDate, String endDate, String cont) throws CommandException {
    	
    	validateToken();
		if(StringUtils.isEmpty(instrument) || StringUtils.isEmpty(candlePeriod) || StringUtils.isEmpty(dateFormat) 
				|| StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(cont))
			throw new CommandException("Invalid input");
        String url = String.format(HISTORICAL_URL1,instrument, candlePeriod, dateFormat, startDate, endDate, cont);

        // Call the API with the access token and parse the JSON response
        HttpResponse<String> response = getHttpResponse(url, accessToken);

        if (response.statusCode() == 200) {
        	parseJsonResponse(response);
        }
    }

    
    private HttpResponse<String> getHttpResponse(String url, String accessToken) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", APPID + ":" + accessToken)
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response;
            } else {
                logger.log(Level.SEVERE, "Error: Received HTTP status code " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred while sending GET request: ", e);
            return null;
        }
    }
    
    private static void parseJsonResponse(HttpResponse<String> response) {
        try {
        	List<Candle> candles = new ArrayList<>();
        	JsonObject jsonObjectGoogle = JsonParser.parseString(response.body()).getAsJsonObject();
        	Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonPrettyPrint = gson.toJson(jsonObjectGoogle);
            System.out.println(jsonPrettyPrint);
        	Iterator<JsonElement> elementI = jsonObjectGoogle.get("candles").getAsJsonArray().iterator();
    		while(elementI.hasNext()) {
    			JsonElement next = elementI.next();
    			JsonArray d = next.getAsJsonArray();
    			candles.add(new Candle(
    					d.get(0).getAsLong(), 
    					d.get(1).getAsFloat(),
    					d.get(2).getAsFloat(),
    					d.get(3).getAsFloat(),
    					d.get(4).getAsFloat(),
    					d.get(5).getAsLong()
    				));
    		}
    		//return candles;
    		for(Candle c : candles) {
    			System.out.println(c);
    		}

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing JSON response: ", e);
        }
    }
    
    private void validateToken() throws CommandException {
		if (StringUtils.isEmpty(this.accessToken))
			throw new CommandException("Accesstoken is not generated.");

	}
}