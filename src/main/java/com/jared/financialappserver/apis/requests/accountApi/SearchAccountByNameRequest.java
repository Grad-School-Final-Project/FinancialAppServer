package com.jared.financialappserver.apis.requests.accountApi;

import com.jared.financialappserver.models.dto.UserDTO;

public class SearchAccountByNameRequest {
    private UserDTO user;

    private String accountNameToSearch;
}
