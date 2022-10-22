package com.jared.financialappserver.apis.controllers.interfaces;

import com.jared.financialappserver.apis.requests.accountApi.ChangeAccountNameRequest;
import com.jared.financialappserver.apis.requests.accountApi.CreateAccountRequest;
import com.jared.financialappserver.models.dto.AccountDTO;
import com.jared.financialappserver.models.dto.AccountTypeEnum;
import com.jared.financialappserver.models.dto.UserDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountAPI {

    /**
     * Creates an account from the AccountDTO passed in
     *
     * @param account the account to create.
     * @return The account that was created, null if account was unable to be created.
     */
    public AccountDTO addAccount(CreateAccountRequest request);

    public AccountDTO changeAccountName(ChangeAccountNameRequest request);

    public boolean deleteAccount(AccountDTO account);

    public List<AccountDTO> getAllUserAccounts(UserDTO user);

    public List<AccountDTO> searchAccountsByName(UserDTO user, String searchName);

    public Map<AccountDTO, BigDecimal> getAccountValues(List<AccountDTO> accounts);

    List<AccountTypeEnum> getAllAccountTypes();

}
