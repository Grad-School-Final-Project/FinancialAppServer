package com.jared.financialappserver.apis.controllers.interfaces;

import com.jared.financialappserver.apis.requests.BudgetSpendingRequest;
import com.jared.financialappserver.models.dto.BudgetDTO;
import com.jared.financialappserver.models.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.List;
import java.util.Map;


public interface BudgetAPI {

    public BudgetDTO createBudget(BudgetDTO budget);

    public BudgetDTO modifyBudget(BudgetDTO budget);

    public List<BudgetDTO> getUserBudgets(UserDTO user);

    public Map<Integer, Double> getBudgetSpending(BudgetSpendingRequest request);

}
