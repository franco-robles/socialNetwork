package com.pipepiper.socialnetwork.controllers;

import com.pipepiper.socialnetwork.models.User;
import com.pipepiper.socialnetwork.repositorys.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController//permite trabajar con json
//@RequestMapping("/api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository repository;

    @GetMapping("/broh")
    public String helloWorld() {
        return "What's up BRO!!!";
    }


    //CRUD for User

    //Create an User
    /** FALTA: si un email ya esta registrado no tiene qe permitir el registro **/
    @PostMapping("/register")
    public ResponseEntity<String> create(@RequestBody User user){

        if(user.getId()!=null){//si id no es null, tiene que dar un error porque el id se crea automaticamente
            log.warn("error trying to create user: user id is not null");
            return ResponseEntity.badRequest().build();
        }else{
            repository.save(user);
            return ResponseEntity.ok().build();
        }

    }


    //Read an User from DB
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        Optional<User> userOpt = repository.findById(id);

        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        }else{
            log.warn("error getting user: id does not exist");
            return ResponseEntity.notFound().build();
        }
        // lo mismo pero con programacion funcional
        // return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Read all User from DB
    @GetMapping("/users")
    public ResponseEntity<List<User>> getById(){
        List<User> listUser = repository.findAll();

        if (listUser.size()!=0) {
            return ResponseEntity.ok(listUser);
        }else{
            log.warn("error getting users: no one user");
            return ResponseEntity.notFound().build();
        }
        // lo mismo pero con programacion funcional
        // return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Update an User
    /** FALTA: Necesito que el update se haga solo sobre los datos que no son null **/
    @PutMapping("/user/update")
    public ResponseEntity<User> updateById(@RequestBody User user){

        if(user.getId()==null){//primero quiero saber si la peticion es correcta
            log.warn("error trying to update user: id is null");
            return ResponseEntity.badRequest().build();
        }else if (!repository.existsById(user.getId())){//si el usuario no existe
            log.warn("error trying to update user: user does not exist");
            return ResponseEntity.notFound().build();
        }
        repository.save(user);
        return ResponseEntity.ok().build();
    }

    //Delete an User
    /** ME GUSTARIA: que solo se puedan borrar el propio usuario (para eso falta coneccion a DB)**/
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        Optional<User> userOpt = repository.findById(id);
        if (userOpt.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok("User was eliminated");
        }else{
            //User not Found
            log.warn("error trying to delete user: user does not exist");
            return ResponseEntity.notFound().build();
        }
    }


}
