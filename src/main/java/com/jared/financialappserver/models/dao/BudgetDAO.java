package com.jared.financialappserver.models.dao;

import com.jared.financialappserver.models.dto.BudgetDTO;
import com.jared.financialappserver.models.dto.TransactionDTO;
import com.jared.financialappserver.models.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BudgetDAO extends CrudRepository<BudgetDTO, Integer> {

    public List<BudgetDTO> findBudgetDTOByUser(UserDTO user);
}
