package com.example.demo.repository;

import com.example.demo.entities.UserRole;
import com.example.demo.entities.UserRoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole,Integer> {


    Optional<UserRole> findByUserRoleName(UserRoleName userRoleName);
}
