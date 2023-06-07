/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.repository;

import com.droneservice.droneservice.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Hp
 */
public interface UserRepository extends JpaRepository<User, Long>{
      Optional<User> findByUserName(String username);
     Optional<User> findByUserNameAndUserPassword(String username,String password);
}
