package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idRole;

    @Enumerated(EnumType.STRING)
    private UserRoleName userRoleName;

    //access

    private Boolean userManagement;
    private Boolean eventManagement;


    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public Boolean getUserManagement() {
        return userManagement;
    }

    public void setUserManagement(Boolean userManagement) {
        this.userManagement = userManagement;
    }

    public UserRoleName getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(UserRoleName userRoleName) {
        this.userRoleName = userRoleName;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Boolean getEventManagement() {
        return eventManagement;
    }

    public void setEventManagement(Boolean eventManagement) {
        this.eventManagement = eventManagement;
    }
}
