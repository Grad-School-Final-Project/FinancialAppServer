package com.jared.financialappserver.apis.requests;

import com.jared.financialappserver.models.dto.BudgetDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BudgetSpendingRequest {

    List<BudgetDTO> budgetDTOs;

    Month month;
}
