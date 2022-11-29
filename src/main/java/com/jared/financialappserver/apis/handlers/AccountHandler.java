package com.jared.financialappserver.apis.handlers;

import com.jared.financialappserver.apis.controllers.interfaces.AccountAPI;
import com.jared.financialappserver.apis.requests.accountApi.ChangeAccountNameRequest;
import com.jared.financialappserver.apis.requests.accountApi.CreateAccountRequest;
import com.jared.financialappserver.models.dao.AccountDAO;
import com.jared.financialappserver.models.dao.TransactionDAO;
import com.jared.financialappserver.models.dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountHandler implements AccountAPI {
    private static final Logger logger = LogManager.getLogger(AccountHandler.class);

    private AccountDAO accountDAO;

    private TransactionDAO transactionDAO;

    @Override
    public AccountDTO addAccount(CreateAccountRequest request) {
        AccountDTO createdAccount = accountDAO.save(request.getAccountDTO());
        if(request.getAccountDTO().getAccount_type() != AccountTypeEnum.BROKERAGE)
        {
            TransactionDTO initialBalanceTransaction = TransactionDTO.builder()
                    .associatedAccount(createdAccount)
                    .notes("")
                    .amount(request.getInitialBalance())
                    .currency("USD")
                    .description("Initial Balance Transaction")
                    .category(CategoryDTO.getHideFromBudgetsCategory())
                    .date(new Date(Calendar.getInstance().getTimeInMillis()))
                    .build();
            transactionDAO.save(initialBalanceTransaction);
        }


        return createdAccount;
    }

    @Override
    public AccountDTO changeAccountName(ChangeAccountNameRequest request) {
        Optional<AccountDTO> query = accountDAO.findById(request.getOriginalAccount().getAccount_id());
        if(query.isEmpty())
        {
            return null;
        }

        AccountDTO account = query.get();
        account.setAccount_nickname(request.getNewName());

        accountDAO.save(account);
        return account;
    }

    @Override
    public boolean deleteAccount(AccountDTO account) {
        Optional<AccountDTO> query = accountDAO.findById(account.getAccount_id());
        if(query.isEmpty())
        {
            return false;
        }
        accountDAO.delete(query.get());
        return true;
    }

    @Override
    public List<AccountDTO> getAllUserAccounts(UserDTO user) {
        return accountDAO.findByUser(user);
    }

    @Override
    public List<AccountDTO> searchAccountsByName(UserDTO user, String searchName) {
        return null;
    }

    @Override
    public Map<AccountDTO, BigDecimal> getAccountValues(List<AccountDTO> accounts) {
        Map<AccountDTO, BigDecimal> accountBalanceMap = new HashMap<>();

        for(AccountDTO account : accounts) {
            List<TransactionDTO> accountTransactions = transactionDAO.findTransactionDTOSByAssociatedAccount(account);
            BigDecimal accountBalance = getAccountBalance(accountTransactions);
            accountBalanceMap.put(account, accountBalance);
        }
        return accountBalanceMap;
    }

    @Override
    public List<AccountTypeEnum> getAllAccountTypes() {
        return Arrays.stream(AccountTypeEnum.values()).collect(Collectors.toList());
    }

    public static BigDecimal getAccountBalance(List<TransactionDTO> accountTransactions) {
        return accountTransactions.stream()
                .map(transaction -> transaction.getAmount())
                .reduce(new BigDecimal(0), (subtotal, bigDec) -> subtotal.add(bigDec));
    }
}
