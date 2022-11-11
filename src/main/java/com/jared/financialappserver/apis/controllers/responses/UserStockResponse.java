package com.jared.financialappserver.apis.controllers.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserStockResponse {

    private String stockTicker;
    private double sharesOwned;
    private double currentPrice;
    private double totalPricePaid;
}
