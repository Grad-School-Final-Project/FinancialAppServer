package com.jared.financialappserver.models.dao;

import com.jared.financialappserver.models.dto.StockDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface StockDAO extends CrudRepository<StockDTO, String> {
}
