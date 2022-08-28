package com.jared.financialappserver.apis.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateUserRequest {
    String username;
    String password;
    String email;
    String firstname;
    String lastname;
}
