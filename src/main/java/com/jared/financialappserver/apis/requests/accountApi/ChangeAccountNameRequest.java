package com.jared.financialappserver.apis.requests.accountApi;

import com.jared.financialappserver.models.dto.AccountDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangeAccountNameRequest {
    private AccountDTO originalAccount;
    private String newName;
}
