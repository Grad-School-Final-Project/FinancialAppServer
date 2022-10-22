package com.jared.financialappserver.models.dto;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "transaction")
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transaction_id;

    private String description;

    @ManyToOne
    private UserDTO user;

    @ManyToOne
    private AccountDTO associatedAccount;

    private String notes;

    @Column(columnDefinition = "numeric(19,2)")
    private BigDecimal amount;

    private String currency;

    @ManyToOne
    private CategoryDTO category;

    private Date date;
}
