package com.jared.financialappserver.models.dto;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "brokerage_transaction")
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BrokerageTransactionDTO{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private AccountDTO accountDTO;

    @ManyToOne
    private StockDTO stockDTO;

    double unitsPurchased;

    double pricePerUnit;

}
