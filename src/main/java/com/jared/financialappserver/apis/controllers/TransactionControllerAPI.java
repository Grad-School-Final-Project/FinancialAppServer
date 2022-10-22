package com.jared.financialappserver.apis.controllers;

import com.jared.financialappserver.apis.controllers.interfaces.TransactionAPI;
import com.jared.financialappserver.apis.handlers.TransactionHandler;
import com.jared.financialappserver.apis.requests.transactionAPI.CreateTransactionsFromCSVRequest;
import com.jared.financialappserver.apis.requests.transactionAPI.UpdateTransactionRequest;
import com.jared.financialappserver.models.dao.AccountDAO;
import com.jared.financialappserver.models.dao.CategoryDAO;
import com.jared.financialappserver.models.dao.TransactionDAO;
import com.jared.financialappserver.models.dto.AccountDTO;
import com.jared.financialappserver.models.dto.BudgetDTO;
import com.jared.financialappserver.models.dto.TransactionDTO;
import com.jared.financialappserver.models.dto.UserDTO;
import com.jared.financialappserver.util.KeycloakUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secured/transactions/")
public class TransactionControllerAPI implements TransactionAPI {

    @Autowired
    private TransactionDAO transactionDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    @PostMapping("createTransaction")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public TransactionDTO createTransaction(@RequestBody TransactionDTO transaction) {
//        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), transaction.getAssociatedAccount().getUser());
        TransactionHandler handler = new TransactionHandler(transactionDAO, categoryDAO);
        return handler.createTransaction(transaction);
    }

    @Override
    @PostMapping("createTransactions")
    public List<TransactionDTO> createTransactions(@RequestBody List<TransactionDTO> transactions) {
        for(TransactionDTO transaction : transactions){
            // each account specified must belong to the user making this request.
            KeycloakUtil.verifyRequest(
                    SecurityContextHolder.getContext().getAuthentication(),
                    transaction.getAssociatedAccount().getUser());
        }
        TransactionHandler handler = new TransactionHandler(transactionDAO);
        return handler.createTransactions(transactions);
    }

    @Override
    @PostMapping("importCSVTransactions")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<TransactionDTO> createTransactionsFromCSV(@RequestBody CreateTransactionsFromCSVRequest request) {
//        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), request.getAccount().getUser());
        TransactionHandler handler = new TransactionHandler(transactionDAO, accountDAO, categoryDAO);
        return handler.createTransactionsFromCSV(request);
    }



    @Override
    @PostMapping("getUserTransactions")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<TransactionDTO> getUserTransactions(@RequestBody UserDTO user) {
        //        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), user);
        TransactionHandler handler = new TransactionHandler(transactionDAO);
        return handler.getUserTransactions(user);
    }

    @Override
    @PostMapping("updateTransaction")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public TransactionDTO updateTransaction(@RequestBody UpdateTransactionRequest request) {
        //        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), user);
        TransactionHandler handler = new TransactionHandler(transactionDAO, accountDAO, categoryDAO);
        return handler.updateTransaction(request);
    }

    @Override
    @PostMapping("deleteTransaction")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public boolean deleteTransaction(@RequestBody TransactionDTO transactionDTO) {
        //        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), user);
        TransactionHandler handler = new TransactionHandler(transactionDAO);
        return handler.deleteTransaction(transactionDTO);
    }

    @Override
    @GetMapping("getAccountTransactions")
    public List<TransactionDTO> getAccountTransactions(@RequestBody AccountDTO account) {
        return null;
    }

    @Override
    @GetMapping("getBudgetTransactions")
    public List<TransactionDTO> getBudgetTransactions(@RequestBody BudgetDTO budget) {
        return null;
    }
}
