package com.api.gestion.apigestionfacturas.pojo;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;



@NamedQuery(name = "User.findByEmail", query = "select u from User u where u.email = :email")
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name ="nombre")
    private String nombre;

    @Column(name = "numeroDeContacto")
    private String numeroDeContacto;

    @Column(name ="email")
    private String email;

    @Column(name ="password")
    private String password;
    
    @Column(name ="status")
    private String status;

    @Column(name ="role")
    private String role;

    
}
