package com.unitec.amigos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class ControladorHola {

    //Este primer recurso es el Hola Mundo de un servicio REST que usa el metodo
    //  GET
    @GetMapping("/Hola")
    public String Saludar(){

        return "Hola desde mi primer servicio REST";
    }
}
