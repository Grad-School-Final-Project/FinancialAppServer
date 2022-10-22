package com.jared.financialappserver.models.dto;

import com.jared.financialappserver.models.dao.CategoryDAO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "category",
        uniqueConstraints = {@UniqueConstraint(columnNames ={"user_id","category_name"})} )
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int category_id;

    @Column(name = "category_name")
    private String category_name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDTO user;

    @ManyToOne(fetch = FetchType.EAGER)
    private CategoryDTO parentCategory;

    public static CategoryDTO getHideFromBudgetsCategory(){
        return CategoryDTO.builder()
                .user(UserDTO.getGenericUser())
                .category_id(1)
                .category_name("Hide from Budgets and Trends")
                .parentCategory(null)
                .build();
    }

    public static CategoryDTO getUncategorizedCategory(){
        return CategoryDTO.builder()
                .user(UserDTO.getGenericUser())
                .category_id(0)
                .category_name("Uncategorized")
                .parentCategory(null)
                .build();
    }


}
