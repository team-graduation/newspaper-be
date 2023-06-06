package com.vn.newspaperbe.repository;

import com.vn.newspaperbe.entity.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<DAOUser, Integer> {
    DAOUser findByUsername(String username);

}
