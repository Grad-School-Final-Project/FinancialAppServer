package com.jared.financialappserver.models.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Date;
@Entity
@Table(name = "stock" )
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    @Id
    private String ticker;

    private String companyName;
}
