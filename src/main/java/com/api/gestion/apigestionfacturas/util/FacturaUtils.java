package com.api.gestion.apigestionfacturas.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FacturaUtils {
    
    private FacturaUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String message , HttpStatus httpStatus){
        return new ResponseEntity<>("Mensaje: " + message, httpStatus);
    }
}

