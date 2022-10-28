package com.jared.financialappserver.apis.controllers;

import com.jared.financialappserver.apis.controllers.interfaces.BudgetAPI;
import com.jared.financialappserver.apis.handlers.BudgetHandler;
import com.jared.financialappserver.apis.requests.BudgetSpendingRequest;
import com.jared.financialappserver.models.dao.BudgetDAO;
import com.jared.financialappserver.models.dao.CategoryDAO;
import com.jared.financialappserver.models.dao.TransactionDAO;
import com.jared.financialappserver.models.dao.UserDAO;
import com.jared.financialappserver.models.dto.BudgetDTO;
import com.jared.financialappserver.models.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.Month;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/secured/budgets/")
public class BudgetControllerAPI implements BudgetAPI {

    @Autowired
    BudgetDAO budgetDAO;

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    CategoryDAO categoryDAO;

    @Override
    @PostMapping("addBudget")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Transactional(rollbackOn = Exception.class)
    public BudgetDTO createBudget(@RequestBody BudgetDTO budget) {
        //        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), request.getAccountDTO().getUser());
        BudgetHandler handler = new BudgetHandler(budgetDAO, transactionDAO, userDAO, categoryDAO);
        return handler.createBudget(budget);
    }

    @Override
    @PostMapping("modifyBudget")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Transactional(rollbackOn = Exception.class)
    public BudgetDTO modifyBudget(@RequestBody BudgetDTO budget) {
        //        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), request.getAccountDTO().getUser());
        BudgetHandler handler = new BudgetHandler(budgetDAO, transactionDAO, userDAO, categoryDAO);
        return handler.modifyBudget(budget);
    }

    @Override
    @PostMapping("getUserBudgets")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Transactional(rollbackOn = Exception.class)
    public List<BudgetDTO> getUserBudgets(@RequestBody UserDTO user) {
        //        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), request.getAccountDTO().getUser());
        BudgetHandler handler = new BudgetHandler(budgetDAO, transactionDAO, userDAO, categoryDAO);
        return handler.getUserBudgets(user);
    }

    @Override
    @PostMapping("getBudgetSpending")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Transactional(rollbackOn = Exception.class)
    public Map<Integer, Double> getBudgetSpending(@RequestBody BudgetSpendingRequest request) {
        //        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), request.getAccountDTO().getUser());
        BudgetHandler handler = new BudgetHandler(budgetDAO, transactionDAO, userDAO, categoryDAO);
        return handler.getBudgetSpending(request);
    }
}
