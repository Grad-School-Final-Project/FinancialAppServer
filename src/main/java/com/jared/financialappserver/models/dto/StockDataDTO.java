package com.jared.financialappserver.models.dto;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "stockData" )
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StockDataDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private StockDTO stock;

    private Date date;

    private double price;

    private double marketCap;

    private String recommendation;

    private double targetHighPrice;

    private double targetLowPrice;

    private double privateMedianPrice;

    private double lastDividendAmount;

    private double currentDividendYield;

}
