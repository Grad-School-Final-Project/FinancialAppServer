package com.jared.financialappserver.apis.handlers;

import com.google.common.base.Ticker;
import com.jared.financialappserver.apis.controllers.responses.UserStockResponse;
import com.jared.financialappserver.apis.requests.stockApi.AddStockPurchaseRequest;
import com.jared.financialappserver.models.dao.*;
import com.jared.financialappserver.models.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;

import javax.naming.AuthenticationException;
import javax.persistence.PersistenceException;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<UserStockResponse> getUserStocks(UserDTO user) {

        List<UserStockResponse> response = new ArrayList<>();
        Map<String, StockUserOwnership> tickers = getUserStockOwnership(user);

        for(String ticker : tickers.keySet())
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
                    .totalPricePaid(tickers.get(ticker).getNetPricePaid())
                    .currentPrice(price)
                    .sharesOwned(tickers.get(ticker).getNumberSharesOwned())
                    .build();
            response.add(userStock);
        }


        return response;
    }

    private Map<String, StockUserOwnership> getUserStockOwnership(UserDTO user) {
        Map<String, StockUserOwnership> stockTickerToNumberOwned = new HashMap<>();

        Iterable<BrokerageTransactionDTO> all = brokerageTransactionDAO.findAll();
        List<BrokerageTransactionDTO> allBrokerageTransactions = new ArrayList<>();
        for(BrokerageTransactionDTO bt : all){
            allBrokerageTransactions.add(bt);
        }

        List<BrokerageTransactionDTO> userStockTransactions = allBrokerageTransactions.stream()
                .filter(t -> t.getAccountDTO().getUser().getUsername().equals(user.getUsername()))
                .collect(Collectors.toList());
        for(BrokerageTransactionDTO userTransaction : userStockTransactions)
        {
            String ticker = userTransaction.getStockDTO().getTicker();
            if(stockTickerToNumberOwned.get(ticker) == null){
                stockTickerToNumberOwned.put(ticker, new StockUserOwnership());
            }

            stockTickerToNumberOwned.get(ticker).addToNumberSharesOwned(userTransaction.getUnitsPurchased());
            stockTickerToNumberOwned.get(ticker).addToNetPricePaid(
                    userTransaction.getUnitsPurchased()*userTransaction.getPricePerUnit());
        }

        return stockTickerToNumberOwned;
    }
}
