package com.jared.financialappserver.models.dto;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account",
        uniqueConstraints = {@UniqueConstraint(columnNames ={"user_id","nickname"})} )
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int account_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDTO user;

    @Column(name = "nickname")
    private String account_nickname;

    private String account_institution;

    @Enumerated(EnumType.STRING)
    private AccountTypeEnum account_type;

}
