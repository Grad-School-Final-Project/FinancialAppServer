package com.jared.financialappserver.apis.requests.stockApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddStockPurchaseRequest {

    private String username;
    private String stockTicker;
    private int associatedAccountId;
    private double unitsPurchased;
    private double pricePerUnit;
    private Date date;
}
