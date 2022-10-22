package com.jared.financialappserver.apis.controllers.interfaces;

import com.jared.financialappserver.apis.requests.transactionAPI.CreateTransactionsFromCSVRequest;
import com.jared.financialappserver.apis.requests.transactionAPI.UpdateTransactionRequest;
import com.jared.financialappserver.models.dao.TransactionDAO;
import com.jared.financialappserver.models.dto.AccountDTO;
import com.jared.financialappserver.models.dto.BudgetDTO;
import com.jared.financialappserver.models.dto.TransactionDTO;
import com.jared.financialappserver.models.dto.UserDTO;

import java.util.List;

public interface TransactionAPI {
    public TransactionDTO createTransaction(TransactionDTO transaction);

    public List<TransactionDTO> createTransactions(List<TransactionDTO> transactions);

    public List<TransactionDTO> createTransactionsFromCSV(CreateTransactionsFromCSVRequest request);

    public List<TransactionDTO> getUserTransactions(UserDTO user);

    public TransactionDTO updateTransaction(UpdateTransactionRequest transactionDTO);

    public boolean deleteTransaction(TransactionDTO transactionDTO);

    public List<TransactionDTO> getAccountTransactions(AccountDTO account);

    public List<TransactionDTO>  getBudgetTransactions(BudgetDTO budget);




}
