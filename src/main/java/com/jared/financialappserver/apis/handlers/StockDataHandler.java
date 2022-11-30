package com.jared.financialappserver.apis.handlers;

import com.jared.financialappserver.apis.controllers.HelloWorldController;
import com.jared.financialappserver.models.dao.StockDAO;
import com.jared.financialappserver.models.dao.StockDataDAO;
import com.jared.financialappserver.models.dto.StockDTO;
import com.jared.financialappserver.models.dto.StockDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.james.mime4j.field.datetime.DateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.xml.datatype.Duration;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;


@Getter
@AllArgsConstructor
public class StockDataHandler {

    private static final Logger logger = LogManager.getLogger(StockDataHandler.class);
    StockDataDAO stockDataDAO;

    public double getCurrentStockPrice(StockDTO stock)
    {
        StockDataDTO mostRecentStockData = stockDataDAO.findMostUpToDateDataForTicker(stock.getTicker());

        if (mostRecentStockData== null
                    || mostRecentStockData.getInstant().isBefore(Instant.now().minus(20, ChronoUnit.MINUTES)))
        {
            try {
                mostRecentStockData = requestStockPrice(stock);
                stockDataDAO.save(mostRecentStockData);
            } catch (Exception e) {
                System.out.println("Issue saving latest stock data to DB");
            }
        }
        return mostRecentStockData.getPrice();
    }


    private StockDataDTO requestStockPrice(StockDTO stock) throws IOException, InterruptedException {
        String apiKey = System.getenv("FINHUB_API_KEY");
        String url = "https://finnhub.io/api/v1/quote?symbol=" +
                stock.getTicker().toUpperCase() +
                "&token=" + apiKey;


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        logger.debug("FinHub response: " + response);
        if(response.statusCode() != 200)
        {
            throw new IOException("Bad response code");
        }
        logger.debug("Response body: " + response.body());
        JSONObject object = new JSONObject(response.body());
        BigDecimal currentPrice = (BigDecimal) object.get("c");
        logger.debug("Current price: " + stock.getTicker() + ":" + currentPrice);
        int time = (Integer) object.get("t");

        return StockDataDTO.builder()
                .stock(stock)
                .price(currentPrice.doubleValue())
                .instant(Instant.ofEpochSecond(time))
                .build();
    }
}
