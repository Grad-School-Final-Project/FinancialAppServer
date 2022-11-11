package com.jared.financialappserver.models.dto;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.Instant;

@Entity
@Table(name = "stock_data",
        uniqueConstraints = {@UniqueConstraint(columnNames ={"stock_ticker","instant"})} )
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
    @JoinColumn(name = "stock_ticker", nullable = false)
    private StockDTO stock;

    private Instant instant;

    private double price;

}
