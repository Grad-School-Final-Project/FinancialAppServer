package com.jared.financialappserver.apis.handlers;

import com.jared.financialappserver.apis.controllers.interfaces.BudgetAPI;
import com.jared.financialappserver.apis.requests.BudgetSpendingRequest;
import com.jared.financialappserver.models.dao.BudgetDAO;
import com.jared.financialappserver.models.dao.CategoryDAO;
import com.jared.financialappserver.models.dao.TransactionDAO;
import com.jared.financialappserver.models.dao.UserDAO;
import com.jared.financialappserver.models.dto.BudgetDTO;
import com.jared.financialappserver.models.dto.CategoryDTO;
import com.jared.financialappserver.models.dto.TransactionDTO;
import com.jared.financialappserver.models.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class BudgetHandler implements BudgetAPI {

    private BudgetDAO budgetDAO;

    private TransactionDAO transactionDAO;

    private UserDAO userDAO;

    private CategoryDAO categoryDAO;

    @Override
    public BudgetDTO createBudget(BudgetDTO budget) {
        BudgetDTO budgetToSave = updatedAssociatedEntities(budget);
        budgetDAO.save(budgetToSave);
        return budgetToSave;
    }

    @Override
    public BudgetDTO modifyBudget(BudgetDTO budget) {
        BudgetDTO budgetToSave = updatedAssociatedEntities(budget);
        budgetDAO.save(budgetToSave);
        return budgetToSave;
    }

    @Override
    public List<BudgetDTO> getUserBudgets(UserDTO user) {
        Optional<UserDTO> userToUse = userDAO.findById(user.getUsername());
        if(userToUse.isEmpty()){
            throw new PersistenceException("Unable to find user with username: " + user.getUsername());
        }

        return budgetDAO.findBudgetDTOByUser(userToUse.get());
    }

    @Override
    public Map<Integer, Double> getBudgetSpending(BudgetSpendingRequest request) {
        List<BudgetDTO> budgetDTOs = request.getBudgetDTOs();
        Month month = request.getMonth();
        Map<Integer, Double> budgetMap = new HashMap<>();
        for(BudgetDTO budget : budgetDTOs){
            TransactionHandler tHandler = new TransactionHandler(transactionDAO, null, categoryDAO, userDAO);
            List<TransactionDTO> transactionDTOs = tHandler.getBudgetTransactions(budget);
            transactionDTOs = transactionDTOs.stream().filter(t -> t.getDate().toLocalDate().getMonth() == month).collect(Collectors.toList());

            double transactionsValue = 0;
            for(TransactionDTO t: transactionDTOs)
            {
                transactionsValue += t.getAmount().doubleValue();
            }
            budgetMap.put(budget.getBudget_id(), transactionsValue);
        }

        return budgetMap;
    }

    private BudgetDTO updatedAssociatedEntities(BudgetDTO budget)
    {
        Optional<CategoryDTO> optionalCategoryDTO = categoryDAO.findById(budget.getAssociated_category().getCategory_id());
        if(optionalCategoryDTO.isEmpty())
        {
            throw new PersistenceException("Could not find category with id " + budget.getAssociated_category().getCategory_id());
        }
        Optional<UserDTO> optionalUserDTO = userDAO.findById(budget.getUser().getUsername());
        if(optionalUserDTO.isEmpty())
        {
            throw new PersistenceException("Could not find user with username: " + budget.getUser().getUsername());
        }
        budget.setAssociated_category(optionalCategoryDTO.get());
        budget.setUser(optionalUserDTO.get());
        return budget;
    }
}
