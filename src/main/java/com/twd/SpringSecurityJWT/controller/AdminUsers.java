package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminUsers {

    @Autowired
    private AuthService authService;

    @PutMapping("/user/update-profile/{id}")
    public ResponseEntity<ReqRes> updateProfile(@PathVariable Integer id, @RequestBody ReqRes updateRequest) {
        ReqRes response = authService.updateProfile(id, updateRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/user/delete-user/{id}")
    public ResponseEntity<ReqRes> deleteUser(@PathVariable Integer id) {
        ReqRes response = authService.deleteUser(id);

        if (response.getStatusCode() == HttpStatus.OK.value()) {
            response.setMessage("L'utilisateur avec l'ID " + id + " a été supprimé avec succès.");
        } else {
            response.setMessage("La suppression de l'utilisateur avec l'ID " + id + " a échoué.");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/user/alone")
    public ResponseEntity<Object> userAlone(){
        return ResponseEntity.ok("USers alone can access this ApI only");
    }

    @GetMapping("/adminuser/both")
    public ResponseEntity<Object> bothAdminaAndUsersApi(){
        return ResponseEntity.ok("Both Admin and Users Can  access the api");
    }

    /** You can use this to get the details(name,email,role,ip, e.t.c) of user accessing the service*/
    @GetMapping("/public/email")
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication); //get all details(name,email,password,roles e.t.c) of the user
        System.out.println(authentication.getDetails()); // get remote ip
        System.out.println(authentication.getName()); //returns the email because the email is the unique identifier
        return authentication.getName(); // returns the email
    }
}
