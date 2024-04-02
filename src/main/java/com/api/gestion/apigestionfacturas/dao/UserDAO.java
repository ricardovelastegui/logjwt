package com.api.gestion.apigestionfacturas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.gestion.apigestionfacturas.pojo.User;

@Repository

public interface UserDAO extends JpaRepository<User, Long>  {

    //metodo personalizado
    User findByEmail(@Param("email") String email);
    
}
