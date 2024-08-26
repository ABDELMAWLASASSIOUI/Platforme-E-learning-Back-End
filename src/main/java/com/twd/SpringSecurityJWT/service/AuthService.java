package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.repository.OurUserRepo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    @Autowired
    private OurUserRepo ourUserRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    private Set<String> blacklistedTokens = new HashSet<>();

    public ReqRes signUp(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            // Check if the user already exists
            Optional<OurUsers> existingUser = ourUserRepo.findByEmail(registrationRequest.getEmail());
            if (existingUser.isPresent()) {
                resp.setMessage("User with this email already exists");
                resp.setStatusCode(400);
                return resp;
            }

            // Create a new user and set the role to USER by default
            OurUsers ourUsers = new OurUsers();
            ourUsers.setEmail(registrationRequest.getEmail());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUsers.setBirth_date(registrationRequest.getBirth_date());
            ourUsers.setAddress(registrationRequest.getAddress());
            ourUsers.setName(registrationRequest.getName());

            // Set the role to USER by default, you can also check if it's null or empty first
            ourUsers.setRole("USER");

            // Save the new user
            OurUsers ourUserResult = ourUserRepo.save(ourUsers);
            if (ourUserResult != null && ourUserResult.getId() > 0) {
                resp.setOurUsers(ourUserResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError("An unexpected error occurred: " + e.getMessage());
        }
        return resp;
    }




    public ReqRes signIn(ReqRes signinRequest){
        ReqRes response = new ReqRes();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword()));
            var user = ourUserRepo.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: "+ user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
        OurUsers users = ourUserRepo.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }

    //add
    // Méthode pour mettre à jour le profil
    public ReqRes updateProfile(Integer id, ReqRes updateRequest) {
        ReqRes response = new ReqRes();
        try {
            OurUsers user = ourUserRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            user.setEmail(updateRequest.getEmail());
            if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
            }
            user.setImage(updateRequest.getImage());
            user.setAddress(updateRequest.getAddress());
            user.setBirth_date(updateRequest.getBirth_date());
            OurUsers updatedUser = ourUserRepo.save(user);
            response.setOurUsers(updatedUser);
            response.setStatusCode(200);
            response.setMessage("User Profile Updated Successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    // Méthode pour supprimer un utilisateur
    public ReqRes deleteUser(Integer id) {
        ReqRes response = new ReqRes();
        try {
            OurUsers user = ourUserRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            ourUserRepo.delete(user);
            response.setStatusCode(200);
            response.setMessage("User Deleted Successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }








}
