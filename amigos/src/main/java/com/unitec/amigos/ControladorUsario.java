package com.unitec.amigos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorUsario {
    //Metodo que representa cada uno de los estados que vamos a trasferir.
    //Metodos: GET, POST, PUT, DELETE. (Como minimo)

    //Aqui viene ya el uso de la inversion de control
    @Autowired RepositorioUsuario repoUsuario;

    //Implementamos el codigo para guardar un usario en mongodb
    @PostMapping("/usuario")
    public Estatus guardar(@RequestBody String json)throws Exception{
            //Se tiene que leer al objeto y convertirlo a objeto a JAVA
        ObjectMapper mapper = new ObjectMapper();
        Usuario u = mapper.readValue(json, Usuario.class);
            //Usuario guardado en mongo db
        repoUsuario.save(u);
            //Creamos un objeto de tipo Status y este se retorna a Android o Postman
        Estatus estatus = new Estatus();
        estatus.setSuccess(true);
        estatus.setMensaje("Tu usuario se guardo con exito");
        return estatus;
    }

    @GetMapping("/usuario/{id}")
    public Usuario obtenerPorId(@PathVariable String id){
        //Leemos un usuario con el metodo find by id apoyndonos de repoUsuario
        Usuario u = repoUsuario.findById(id).get();
        return u;
    }

    @GetMapping("/usuario")
    public List<Usuario> buscarTodos(){
        return repoUsuario.findAll();
    }

    //Metodo para actualizar usuarios
    @PutMapping("/usuario")
    public Estatus actualizar(@RequestBody String json)throws Exception{
        //Primero se debe verificar que exista el usuario
        ObjectMapper mapper = new ObjectMapper();
        Usuario u = mapper.readValue(json, Usuario.class);
        Estatus e = new Estatus();
        if (repoUsuario.findById(u.getEmail()).isPresent()){
            //Lo volvemos a guardar
            repoUsuario.save(u);
            e.setMensaje("Usuario Actualizado con Exito");
            e.setSuccess(true);
        }else {
            e.setMensaje("Ese usuario no existe");
            e.setSuccess(false);
        }
        return e;
    }

    @DeleteMapping("/usuario")
    public Estatus borrar(@PathVariable String id){
        Estatus estatus = new Estatus();
        if(repoUsuario.findById(id).isPresent()){
            repoUsuario.deleteById(id);
            estatus.setSuccess(true);
            estatus.setMensaje("Usuario Eliminado");
        }else {
            estatus.setSuccess(true);
            estatus.setMensaje("Usuario no existe");
        }
        return estatus;
    }

}
