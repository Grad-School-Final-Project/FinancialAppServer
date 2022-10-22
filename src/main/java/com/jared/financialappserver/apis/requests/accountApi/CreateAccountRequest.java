package com.jared.financialappserver.apis.requests.accountApi;

import com.jared.financialappserver.models.dto.AccountDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CreateAccountRequest {
    AccountDTO accountDTO;
    BigDecimal initialBalance;
}
