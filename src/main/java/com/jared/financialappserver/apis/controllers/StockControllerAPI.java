package com.jared.financialappserver.apis.controllers;

import com.jared.financialappserver.apis.controllers.responses.UserStockResponse;
import com.jared.financialappserver.apis.handlers.StockHandler;
import com.jared.financialappserver.apis.requests.stockApi.AddStockPurchaseRequest;
import com.jared.financialappserver.models.dao.*;
import com.jared.financialappserver.models.dto.BrokerageTransactionDTO;
import com.jared.financialappserver.models.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secured/stocks/")
public class StockControllerAPI {

    @Autowired
    private BrokerageTransactionDAO brokerageTransactionDAO;
    @Autowired
    private StockDAO stockDAO;
    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    StockDataDAO stockDataDAO;

    @PostMapping("addStockPurchase")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public BrokerageTransactionDTO addStockPurchase(@RequestBody AddStockPurchaseRequest request)
    {
        return new StockHandler(brokerageTransactionDAO, stockDAO, accountDAO, userDAO, stockDataDAO).addStockPurchase(request);
    }

    @PostMapping("getUserStocks")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<UserStockResponse> getUserStocks(@RequestBody UserDTO user)
    {
        return new StockHandler(brokerageTransactionDAO, stockDAO, accountDAO, userDAO, stockDataDAO).getUserStocks();
    }
}
