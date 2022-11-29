package com.jared.financialappserver.models.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StockUserOwnership {

    private StockDTO stock;
    private double numberSharesOwned;
    private double netPricePaid;

    public void addToNumberSharesOwned(double sharesToAdd){
        numberSharesOwned += sharesToAdd;
    }

    public void addToNetPricePaid(double valueToAdd){
        netPricePaid += valueToAdd;
    }
}
