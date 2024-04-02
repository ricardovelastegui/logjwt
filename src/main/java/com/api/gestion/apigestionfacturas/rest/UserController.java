package com.api.gestion.apigestionfacturas.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.gestion.apigestionfacturas.constantes.FacturaConstantes;
import com.api.gestion.apigestionfacturas.service.UserService;
import com.api.gestion.apigestionfacturas.util.FacturaUtils;

@RestController
@RequestMapping(path= "/user")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/signUp")
    public ResponseEntity<String>  registrarUsuario(@RequestBody(required = true)Map<String,String> requestMap){
        try{

            return userService.signUp(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = true)  Map<String, String> requeMap){

        try{

            return userService.login(requeMap);

        } catch(Exception e){
            e.printStackTrace();

        }
        return FacturaUtils.getResponseEntity( FacturaConstantes.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR );

    }


}
