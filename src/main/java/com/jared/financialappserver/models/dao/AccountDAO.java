package com.jared.financialappserver.models.dao;

import com.jared.financialappserver.models.dto.AccountDTO;
import com.jared.financialappserver.models.dto.TransactionDTO;
import com.jared.financialappserver.models.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AccountDAO extends CrudRepository<AccountDTO, Integer> {

    List<AccountDTO> findByUser(UserDTO user);
}
