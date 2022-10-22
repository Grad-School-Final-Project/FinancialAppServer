package com.jared.financialappserver.apis.requests.transactionAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateTransactionRequest {
    int transactionId;
    String newDescription;
    String newCategoryName;
}
