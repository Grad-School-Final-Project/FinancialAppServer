package com.jared.financialappserver.models.dao;

import com.jared.financialappserver.models.dto.AccountDTO;
import com.jared.financialappserver.models.dto.CategoryDTO;
import com.jared.financialappserver.models.dto.TransactionDTO;
import com.jared.financialappserver.models.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CategoryDAO extends CrudRepository<CategoryDTO, Integer> {

    List<CategoryDTO> findCategoryDTOSByUser(UserDTO user);

    List<CategoryDTO> findCategoryDTOByParentCategory(CategoryDTO parentCategory);

}
