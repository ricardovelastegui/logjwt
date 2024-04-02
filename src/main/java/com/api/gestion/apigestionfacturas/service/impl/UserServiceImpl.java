package com.api.gestion.apigestionfacturas.service.impl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.api.gestion.apigestionfacturas.constantes.FacturaConstantes;
import com.api.gestion.apigestionfacturas.dao.UserDAO;
import com.api.gestion.apigestionfacturas.pojo.User;
import com.api.gestion.apigestionfacturas.securiry.CustomerDetailsService;
import com.api.gestion.apigestionfacturas.securiry.jwt.JwtUtil;
import com.api.gestion.apigestionfacturas.service.UserService;
import com.api.gestion.apigestionfacturas.util.FacturaUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("registro interno de un usuario {}", requestMap);
        try{
            if (validateSignUpMap(requestMap)){
                User user = userDAO.findByEmail(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userDAO.save(getUserFromMap(requestMap));
                    return FacturaUtils.getResponseEntity("usuario registrado correctamente", HttpStatus.CREATED);
                }
                else{
                    return FacturaUtils.getResponseEntity("el usuario con ese email ya existe", HttpStatus.BAD_REQUEST);
                }
            }

            else{
                return FacturaUtils.getResponseEntity(FacturaConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }   
        } catch (Exception e){
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap){

        if(requestMap.containsKey("nombre") && requestMap.containsKey("numeroDeContacto") && requestMap.containsKey("email")  && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }   



    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setNombre(requestMap.get("nombre"));
        user.setNumeroDeContacto(requestMap.get("numeroDeContacto"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("true");
        user.setRole("USER");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {

        log.info("dentro de login");
        try{

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );

            if(authentication.isAuthenticated()){
                if(customerDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtil.generateToken(customerDetailsService.getUserDetail().getEmail(), customerDetailsService.getUserDetail().getRole()) +  "\"}" , HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>("{\"mensaje\":\""+"espere la aprobacion del administrador"+"\"}", HttpStatus.BAD_REQUEST);
                }
            }

        } catch(Exception exception){
            log.error("{}", exception);
        }

        return new ResponseEntity<>("{\"mensaje\":\""+"credenciales inorrectas"+"\"}", HttpStatus.BAD_REQUEST);
    }
    
}
