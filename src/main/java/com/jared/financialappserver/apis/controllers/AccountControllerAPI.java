package com.jared.financialappserver.apis.controllers;

import com.jared.financialappserver.apis.controllers.interfaces.AccountAPI;
import com.jared.financialappserver.apis.handlers.AccountHandler;
import com.jared.financialappserver.apis.requests.accountApi.ChangeAccountNameRequest;
import com.jared.financialappserver.apis.requests.accountApi.CreateAccountRequest;
import com.jared.financialappserver.models.dao.AccountDAO;
import com.jared.financialappserver.models.dao.TransactionDAO;
import com.jared.financialappserver.models.dto.AccountDTO;
import com.jared.financialappserver.models.dto.AccountTypeEnum;
import com.jared.financialappserver.models.dto.UserDTO;
import com.jared.financialappserver.util.KeycloakUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/secured/accounts/")
public class AccountControllerAPI {
    private static final Logger logger = LogManager.getLogger(AccountControllerAPI.class);
    @Autowired
    AccountDAO accountDao;
    @Autowired
    TransactionDAO transactionDAO;

    @PostMapping("addAccount")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Transactional(rollbackOn = Exception.class)
    public AccountDTO addAccount(@RequestBody CreateAccountRequest request) {
//        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), request.getAccountDTO().getUser());
        AccountHandler handler = new AccountHandler(accountDao, transactionDAO);
        return handler.addAccount(request);
    }

    @PostMapping("changeAccountName")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public AccountDTO changeAccountName(@RequestBody ChangeAccountNameRequest request) {
        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), request.getOriginalAccount().getUser());
        AccountHandler handler = new AccountHandler(accountDao, transactionDAO);
        return handler.changeAccountName(request);
    }

    @PostMapping("deleteAccount")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public boolean deleteAccount(@RequestBody AccountDTO account) {
        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), account.getUser());
        AccountHandler handler = new AccountHandler(accountDao, transactionDAO);
        return handler.deleteAccount(account);
    }

    @PostMapping("getAllUserAccounts")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<AccountDTO> getAllUserAccounts(@RequestBody UserDTO user) {
//        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), user);
        AccountHandler handler = new AccountHandler(accountDao, transactionDAO);
        return handler.getAllUserAccounts(user);
    }

    @GetMapping("searchAccountsByName")
    public List<AccountDTO> searchAccountsByName(UserDTO user, String searchName) {
        return null;
    }

    @PostMapping("getAccountValues")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public String getAccountValues(@RequestBody  List<AccountDTO> accounts)
    {
        for(AccountDTO a : accounts){
            // each account specified must belong to the user making this request.
//            KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), a.getUser());
        }

        AccountHandler handler = new AccountHandler(accountDao, transactionDAO);

        Map<AccountDTO, BigDecimal> accountValueMap = handler.getAccountValues(accounts);
        Map<Integer, BigDecimal> accountIdToValue = new HashMap<>();
        for(AccountDTO key : accountValueMap.keySet()){
            accountIdToValue.put(key.getAccount_id(), accountValueMap.get(key));
        }

        JSONObject json = new JSONObject(accountIdToValue);


        return json.toString();
    }

    @PostMapping("getAccountTypes")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<AccountTypeEnum> getAccountTypes(){
        AccountAPI handler = new AccountHandler();
        return handler.getAllAccountTypes();
    }
}
