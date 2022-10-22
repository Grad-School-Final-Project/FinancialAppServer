package com.jared.financialappserver.apis.requests.transactionAPI;

import com.jared.financialappserver.models.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionsFromCSVRequest {
    private AccountDTO account;
    private List<String> csv;
    private String currencyCode;
}
