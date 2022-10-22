package com.jared.financialappserver.apis.controllers.interfaces;

import com.jared.financialappserver.apis.requests.categoryApi.CreateCategoryRequest;
import com.jared.financialappserver.models.dto.CategoryDTO;
import com.jared.financialappserver.models.dto.UserDTO;

import java.util.List;

public interface CategoryAPI {
    CategoryDTO createCategory(CreateCategoryRequest request);

    List<CategoryDTO> getAllUserCategories(UserDTO user);
}
