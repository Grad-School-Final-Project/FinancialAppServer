package com.jared.financialappserver.models.dao;
import com.jared.financialappserver.models.dto.AccountDTO;

import com.jared.financialappserver.models.dto.CategoryDTO;
import com.jared.financialappserver.models.dto.TransactionDTO;
import com.jared.financialappserver.models.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface TransactionDAO extends CrudRepository<TransactionDTO, Integer> {

    List<TransactionDTO> findTransactionDTOSByAssociatedAccount(AccountDTO accountDTO);

    List<TransactionDTO> findTransactionDTOSByUser(UserDTO user);

    List<TransactionDTO> findTransactionDTOSByCategory(CategoryDTO category);

}


