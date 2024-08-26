package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.repository.OurUserRepo;
import com.twd.SpringSecurityJWT.service.AuthService;
import com.twd.SpringSecurityJWT.service.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private OurUserRepo ourUserRepo;
    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<ReqRes> signUp(@Valid @RequestBody ReqRes signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        SecurityContextHolder.clearContext();//efface le contexte de sécurité actuel
        request.getSession().invalidate();

        Map<String, String> response = new HashMap<>();
        response.put("message", "You have successfully logged out.");
        response.put("token", token != null ? token : "No token provided");

        return ResponseEntity.ok(response);
    }


    @GetMapping("/me")
    public OurUsers getCurrentUser(@RequestHeader("Authorization") String token) {
        // Extrait le JWT du header Authorization
        String jwtToken = token.substring(7);

        // Extrait l'email (ou le nom d'utilisateur) du token
        String email = jwtUtils.extractUsername(jwtToken);

        // Recherche l'utilisateur par email
        return ourUserRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}
