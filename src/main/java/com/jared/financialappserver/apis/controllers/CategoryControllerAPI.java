package com.jared.financialappserver.apis.controllers;

import com.jared.financialappserver.apis.controllers.interfaces.CategoryAPI;
import com.jared.financialappserver.apis.handlers.CategoryHandler;
import com.jared.financialappserver.apis.requests.categoryApi.CreateCategoryRequest;
import com.jared.financialappserver.models.dao.CategoryDAO;
import com.jared.financialappserver.models.dao.UserDAO;
import com.jared.financialappserver.models.dto.CategoryDTO;
import com.jared.financialappserver.models.dto.UserDTO;
import com.jared.financialappserver.util.KeycloakUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secured/categories/")
public class CategoryControllerAPI {

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private UserDAO userDAO;

    @PostMapping("createCategory")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public CategoryDTO createTransaction(@RequestBody CreateCategoryRequest request) {
//        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), categoryDTO.getUser());
        CategoryAPI handler = new CategoryHandler(categoryDAO);
        return handler.createCategory(request);

    }

    @PostMapping("getUserCategories")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<CategoryDTO> getUserCategories(@RequestBody UserDTO user) {
//        KeycloakUtil.verifyRequest(SecurityContextHolder.getContext().getAuthentication(), user);
        CategoryAPI handler = new CategoryHandler(categoryDAO, userDAO);
        return handler.getAllUserCategories(user);

    }
}
