package com.jared.financialappserver.models.dto;

import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "budget",
        uniqueConstraints = {@UniqueConstraint(columnNames ={"user_id","nickname"})})
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int budget_id;

    @Column(name = "nickname")
    private String budgetNickname;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDTO user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryDTO associated_category;

    private double monthlyAmount;
}
