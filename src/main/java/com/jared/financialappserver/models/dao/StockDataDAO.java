package com.jared.financialappserver.models.dao;

import com.jared.financialappserver.models.dto.StockDataDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface StockDataDAO extends CrudRepository<StockDataDTO, Integer> {

    @Query(
            value = "SELECT * FROM postgres.public.stock_data stockData WHERE stockData.stock_ticker = ?1 " +
                    "AND stockData.instant = (SELECT Max(instant) FROM postgres.public.stock_data)",
            nativeQuery = true)
    public StockDataDTO findMostUpToDateDataForTicker(String ticker);
}


