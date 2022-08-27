package com.jared.financialappserver.models.dao;

import com.jared.financialappserver.models.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDAO extends CrudRepository<UserDTO, String> {
}
