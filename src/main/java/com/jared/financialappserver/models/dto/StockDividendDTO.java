package com.jared.financialappserver.models.dto;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "stockDividend" )
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StockDividendDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private StockDTO stock;

    private Date date;

    private double dividend;
}
