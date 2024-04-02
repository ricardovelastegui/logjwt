package com.api.gestion.apigestionfacturas.securiry;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.gestion.apigestionfacturas.dao.UserDAO;
import com.api.gestion.apigestionfacturas.pojo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerDetailsService implements UserDetailsService{


    @Autowired
    private UserDAO userDAO;

    private User userDetail;
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("dentro de loadUserByUsername {}", username);
        userDetail = (User) userDAO.findByEmail(username);

        if(!Objects.isNull(userDetail)){

            return new org.springframework.security.core.userdetails.User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        }

        else{
            throw new UsernameNotFoundException("usuario no encontrado");
        }
    }

    public User getUserDetail(){
        return userDetail;
    }




    
}
