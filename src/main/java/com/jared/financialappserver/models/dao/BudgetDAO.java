package com.jared.financialappserver.models.dao;

import com.jared.financialappserver.models.dto.TransactionDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BudgetDAO extends CrudRepository<TransactionDTO, Integer> {
}
