package com.jared.financialappserver.apis.handlers;

import com.jared.financialappserver.apis.controllers.CategoryControllerAPI;
import com.jared.financialappserver.apis.controllers.interfaces.CategoryAPI;
import com.jared.financialappserver.apis.requests.categoryApi.CreateCategoryRequest;
import com.jared.financialappserver.models.dao.CategoryDAO;
import com.jared.financialappserver.models.dao.TransactionDAO;
import com.jared.financialappserver.models.dao.UserDAO;
import com.jared.financialappserver.models.dto.CategoryDTO;
import com.jared.financialappserver.models.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class CategoryHandler implements CategoryAPI {

    @Autowired
    CategoryDAO categoryDAO;

    private UserDAO userDAO;

    public CategoryHandler(CategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }

    @Override
    public CategoryDTO createCategory(CreateCategoryRequest request) {

        CategoryDTO categoryToCreate = new CategoryDTO();
        categoryToCreate.setCategory_name(request.getCategoryDTO().getCategory_name());
        categoryToCreate.setUser(request.getCategoryDTO().getUser());
        if(request.getParentCategoryId() != null){
            Optional<CategoryDTO> optional = categoryDAO.findById(request.getParentCategoryId());
            if(optional.isEmpty()){
                categoryToCreate.setParentCategory(null);
            }
            else{
                categoryToCreate.setParentCategory(optional.get());
            }
        }

        return categoryDAO.save(categoryToCreate);
    }

    @Override
    public List<CategoryDTO> getAllUserCategories(UserDTO user) {
        UserDTO userFromDB = userDAO.findUserDTOByUsername(user.getUsername());
        if(userFromDB == null){
            throw new PersistenceException("User does not exist");
        }
        List<CategoryDTO> allCategories = categoryDAO.findCategoryDTOSByUser(userFromDB);
        allCategories.addAll(categoryDAO.findCategoryDTOSByUser(UserDTO.getGenericUser()));
        return allCategories;
    }
}
