package com.jared.financialappserver.apis.handlers;

import com.jared.financialappserver.apis.controllers.responses.UserStockResponse;
import com.jared.financialappserver.apis.requests.stockApi.AddStockPurchaseRequest;
import com.jared.financialappserver.models.dao.*;
import com.jared.financialappserver.models.dto.AccountDTO;
import com.jared.financialappserver.models.dto.BrokerageTransactionDTO;
import com.jared.financialappserver.models.dto.StockDTO;
import lombok.AllArgsConstructor;

import javax.naming.AuthenticationException;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class StockHandler {

    private BrokerageTransactionDAO brokerageTransactionDAO;
    private StockDAO stockDAO;
    private AccountDAO accountDAO;

    private UserDAO userDAO;

    private StockDataDAO stockDataDAO;


    public BrokerageTransactionDTO addStockPurchase(AddStockPurchaseRequest request){
        Optional<StockDTO> optionalStockDTO = stockDAO.findById(request.getStockTicker());
        if(optionalStockDTO.isEmpty()){
            throw new PersistenceException("Stock ticker " + request.getStockTicker() + " does not exist.");
        }
        StockDTO stockDTO = optionalStockDTO.get();
        Optional<AccountDTO> optionalAccountDTO = accountDAO.findById(request.getAssociatedAccountId());
        if(optionalAccountDTO.isEmpty()){
            throw new PersistenceException("Account with id " + request.getAssociatedAccountId() + " does not exist.");
        }
        AccountDTO accountDTO = optionalAccountDTO.get();
        if(!accountDTO.getUser().getUsername().equals(request.getUsername()))
        {
            throw new RuntimeException("User name does not match username on account");
        }

        BrokerageTransactionDTO brokerageTransactionDTO = BrokerageTransactionDTO.builder()
                .stockDTO(stockDTO)
                .accountDTO(accountDTO)
                .pricePerUnit(request.getPricePerUnit())
                .unitsPurchased(request.getUnitsPurchased())
                .build();

        brokerageTransactionDAO.save(brokerageTransactionDTO);
        return brokerageTransactionDTO;
    }

    public List<UserStockResponse> getUserStocks() {

        List<UserStockResponse> response = new ArrayList<>();
        String[] tickers = {"AAPL", "AMZN", "AMD", "TSLA"};

        for(String ticker : tickers)
        {
            StockDataHandler stockData = new StockDataHandler(stockDataDAO);
            Optional<StockDTO> stockDTO = stockDAO.findById(ticker);
            if(stockDTO.isEmpty())
            {
                continue;
            }
            double price = stockData.getCurrentStockPrice(stockDTO.get());

            UserStockResponse userStock = UserStockResponse.builder()
                    .stockTicker(ticker)
                    .totalPricePaid(9*price - 10)
                    .currentPrice(price)
                    .sharesOwned(9)
                    .build();
            response.add(userStock);
        }


        return response;
    }

}
