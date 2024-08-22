package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserProfileController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Update user profile with use ID", description = "Updates the profile of a user based on their ID")
    @PutMapping("/user/update-profile/{id}")
    public ResponseEntity<ReqRes> updateProfile(@PathVariable Integer id, @RequestBody ReqRes updateRequest) {
        ReqRes response = authService.updateProfile(id, updateRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/user/delete-user/{id}")//this api is possible change
    public ResponseEntity<ReqRes> deleteUser(@PathVariable Integer id) {
        ReqRes response = authService.deleteUser(id);

        if (response.getStatusCode() == HttpStatus.OK.value()) {
            response.setMessage("L'utilisateur avec l'ID " + id + " a été supprimé avec succès.");
        } else {
            response.setMessage("La suppression de l'utilisateur avec l'ID " + id + " a échoué.");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



}
