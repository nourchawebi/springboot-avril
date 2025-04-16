package com.example.demo.repository;

import com.example.demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    /* NAMED METHOD */
    List<UserEntity> findByUsernameAndAddressEndingWith(String username, String address);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
    /* JPQL METHOD */
    @Query("SELECT u from  UserEntity u  WHERE u.username=?1")
     UserEntity findbyusernamejpql(String username);
    @Query ("SELECT CASE WHEN COUNT(u)>0 THEN true ELSE false  end  from UserEntity u  WHERE u.username=:username")
    boolean existsByUsernamejpqlboolean(@Param("username") String username);
    /* SQL METHOD */
    @Query(value="SELECT * from  users u  WHERE u.username=?1",nativeQuery = true)
    UserEntity findbyusernamesql(String username);
    @Query(value = "SELECT count(*)>0 FROM users WHERE username=:username ",nativeQuery = true)
    boolean existsByUsernamesqlboolean(@Param("username") String username);
    @Query(value = "select * from users u where u.username like :cle%",nativeQuery = true)
    List<UserEntity> getUserSW(@Param(("cle")) String un);
    @Query(value="select * from users u where u.email like %:domain%",nativeQuery = true)
    List<UserEntity> getUsersByEmail(@Param("domain") String un);



}
