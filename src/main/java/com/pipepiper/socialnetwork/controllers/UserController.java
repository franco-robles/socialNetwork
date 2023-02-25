package com.pipepiper.socialnetwork.controllers;

import com.pipepiper.socialnetwork.dto.UserDto;
import com.pipepiper.socialnetwork.models.User;
import com.pipepiper.socialnetwork.repositorys.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController//permite trabajar con json
@RequestMapping("/api")
public class UserController {

    @Value("${app.jwt.jwtCookie}")
    private String cookieName;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository repository;

    @GetMapping("/broh")
    public String helloWorld() {
        return "BRO!!!";
    }


    //CRUD for User

    //CREATE esta en authController

    //Read an User from DB
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id){
        Optional<User> userOpt = repository.findById(id);

        if (userOpt.isPresent()) {
            return ResponseEntity.ok(new UserDto(userOpt.get()));
        }else{
            log.warn("error getting user: id does not exist");
            return ResponseEntity.notFound().build();
        }
        // lo mismo pero con programacion funcional
        // return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Read all User from DB
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> listUser = userToDto(repository.findAll());

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
    @PutMapping("/user/update")
    public ResponseEntity<?> updateById(@RequestBody User user){

        if(user.getId()==null){//primero quiero saber si la peticion es correcta
            log.warn("error trying to update user: id is null");
            return ResponseEntity.badRequest().build();
        }else if (!repository.existsById(user.getId())){// comprueba si el usuario no existe
            log.warn("error trying to update user: user does not exist");
            return ResponseEntity.notFound().build();
        }

        repository.save(user); //peticion correcta y el usuario existe
        return ResponseEntity.ok().build();
    }

    //Delete an User
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

    //pasar list de User a un List de UserDto
    public List<UserDto> userToDto(List<User> users) {
        List<UserDto> newUsersList =  new ArrayList<>();
        for (User user:users ){
            newUsersList.add(new UserDto(user));
        }
        return newUsersList;
    }
}
