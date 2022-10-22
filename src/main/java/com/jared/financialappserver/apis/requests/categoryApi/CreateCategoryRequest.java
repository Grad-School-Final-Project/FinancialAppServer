package com.jared.financialappserver.apis.requests.categoryApi;

import com.jared.financialappserver.models.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
    CategoryDTO categoryDTO;
    int parentCategoryId;
}
