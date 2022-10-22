package com.jared.financialappserver.apis.handlers;

import com.google.common.collect.Lists;
import com.jared.financialappserver.apis.controllers.interfaces.TransactionAPI;
import com.jared.financialappserver.apis.requests.transactionAPI.CreateTransactionsFromCSVRequest;
import com.jared.financialappserver.apis.requests.transactionAPI.UpdateTransactionRequest;
import com.jared.financialappserver.models.dao.AccountDAO;
import com.jared.financialappserver.models.dao.CategoryDAO;
import com.jared.financialappserver.models.dao.TransactionDAO;
import com.jared.financialappserver.models.dto.*;
import com.jared.financialappserver.models.dto.csvFormats.CSVToTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.PersistenceException;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class TransactionHandler implements TransactionAPI {
    private TransactionDAO dao;

    private AccountDAO accountDAO;
    private CategoryDAO categoryDAO;

    public TransactionHandler(TransactionDAO transactionDAO){
        this.dao = transactionDAO;
    }
    public TransactionHandler(TransactionDAO transactionDAO, CategoryDAO categoryDAO){

        this.dao = transactionDAO;
        this.categoryDAO = categoryDAO;
    }
    @Override
    public TransactionDTO createTransaction(TransactionDTO transaction) {
        Optional<CategoryDTO> categoryDTOOptional =
                categoryDAO.findById(transaction.getCategory().getCategory_id());
        if(categoryDTOOptional.isEmpty()){
            throw new PersistenceException("Category with ID: " + transaction.getCategory().getCategory_id() + " not  found");
        }
        transaction.setCategory(categoryDTOOptional.get());
        transaction.setUser(transaction.getAssociatedAccount().getUser());
        dao.save(transaction);
        return transaction;
    }

    @Override
    public List<TransactionDTO> createTransactions(List<TransactionDTO> transactions) {
        List<TransactionDTO> transactionsCreated = new ArrayList<>(transactions.size());
        for(TransactionDTO transaction : transactions){
            transactionsCreated.add(createTransaction(transaction));
        }

        return transactionsCreated;
    }

    @Override
    public List<TransactionDTO> createTransactionsFromCSV(CreateTransactionsFromCSVRequest request) {
        StringBuilder csv = new StringBuilder();
        for (String line : request.getCsv()){
            if(line.trim().isEmpty()){
                continue;
            }
            csv.append(line).append("\n");
        }
        Optional<AccountDTO> account = accountDAO.findById(request.getAccount().getAccount_id());
        if(account.isEmpty()){
            throw new PersistenceException("Account ID passed in does not exist in database");
        }
        List<TransactionDTO> dtos = CSVToTransaction.createTransactionsFromCSV(
                csv.toString(), account.get(), request.getCurrencyCode(), new HashMap<>());
        dao.saveAll(dtos);
        return dtos;
    }

    @Override
    public List<TransactionDTO> getUserTransactions(UserDTO user) {
        List<TransactionDTO> transactionDTOS = dao.findTransactionDTOSByUser(user);
        transactionDTOS.sort(Comparator.comparing(TransactionDTO::getDate));
        return Lists.reverse(transactionDTOS);
    }

    @Override
    public TransactionDTO updateTransaction(UpdateTransactionRequest request) {
        Optional<TransactionDTO> optionalTransactionDTO = dao.findById(request.getTransactionId());
        if(optionalTransactionDTO.isEmpty()){
            throw new PersistenceException("Unable to find transaction with id: " + request.getTransactionId());
        }
        TransactionDTO dtoToUpdate = optionalTransactionDTO.get();
        dtoToUpdate.setDescription(request.getNewDescription());
        List<CategoryDTO> categoryDTOs = categoryDAO.findCategoryDTOSByUser(dtoToUpdate.getUser());
        categoryDTOs.addAll(categoryDAO.findCategoryDTOSByUser(UserDTO.getGenericUser()));
        Optional<CategoryDTO> categoryDTO = categoryDTOs.stream().filter(c -> c.getCategory_name().equals(request.getNewCategoryName())).findFirst();
        if(categoryDTO.isEmpty()){
            throw new PersistenceException("Could not find category with name and user: "
                    + request.getNewCategoryName() + " " + dtoToUpdate.getUser().getUsername());
        }
        dtoToUpdate.setCategory(categoryDTO.get());
        dao.save(dtoToUpdate);
        return dtoToUpdate;
    }

    @Override
    public boolean deleteTransaction(TransactionDTO transactionDTO) {
        Optional<TransactionDTO> transActionToDelete = dao.findById(transactionDTO.getTransaction_id());
        if(transActionToDelete.isEmpty())
        {
            throw new PersistenceException("Unable to find transaction with id " + transactionDTO.getTransaction_id());
        }
        dao.delete(transActionToDelete.get());
        return true;
    }

    @Override
    public List<TransactionDTO> getAccountTransactions(AccountDTO account) {
        return null;
    }

    @Override
    public List<TransactionDTO> getBudgetTransactions(BudgetDTO budget) {
        return null;
    }
}
