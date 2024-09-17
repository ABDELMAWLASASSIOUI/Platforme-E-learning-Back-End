package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.entity.Image;
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
    @Autowired
    private ImageService imageService;
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
            Image image=imageService.getImage(updateRequest.getImageId());
            user.setImage(image);
            user.setAddress(updateRequest.getAddress());
            user.setName(updateRequest.getName());
            //user.setBirth_date(updateRequest.getBirth_date());
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

/*
    public ReqRes getUserById(Integer id) {
        ReqRes response = new ReqRes();
        try {
            OurUsers user = ourUserRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            response.setOurUsers(user);
            response.setStatusCode(200);
            response.setMessage("User retrieved successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

 */
public ReqRes getUserById(Integer id) {
    ReqRes response = new ReqRes();
    try {
        OurUsers user = ourUserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set user details in response
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());  // Be cautious about returning passwords in responses
        response.setAddress(user.getAddress());
        response.setBirth_date(user.getBirth_date());

        // Handle image data if available
        Image image = user.getImage();
        if (image != null) {
            String encodedString = Base64.getEncoder().encodeToString(image.getData());
            response.setImageData("data:" + image.getType() + ";base64," + encodedString);
            response.setImageId(image.getId());  // Set the image ID
        }

        response.setStatusCode(200);
        response.setMessage("User retrieved successfully");
        response.setOurUsers(user);

    } catch (RuntimeException e) {
        response.setStatusCode(404);
        response.setError(e.getMessage());
    } catch (Exception e) {
        response.setStatusCode(500);
        response.setError("An unexpected error occurred: " + e.getMessage());
    }
    return response;
}



    /*

       public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setName(course.getName());
            courseDTO.setDescription(course.getDescription());
            courseDTO.setCategoryId(course.getCategory().getId());
            courseDTO.setOurUsersId(Long.valueOf(course.getOurUsers().getId()));

            // Convert image data to Base64 string
            Image image = course.getImage();
            if (image != null) {
                //String base64Image = Base64Utils.encodeToString(image.getData());
                String encodedString = Base64.getEncoder().encodeToString(image.getData());
                courseDTO.setImageData("data:" + image.getType() + ";base64," + encodedString);
                courseDTO.setImageId(image.getId());  // Set the image ID
            }
            // Add chapters to the DTO
            List<ChapterDTO> chapterDTOs = course.getChapters().stream().map(chapter -> {
                ChapterDTO chapterDTO = new ChapterDTO();
                chapterDTO.setId(chapter.getId()); // Set the chapter ID here
                chapterDTO.setTitle(chapter.getTitle());
                chapterDTO.setContent(chapter.getContent());
                return chapterDTO;
            }).collect(Collectors.toList());
            courseDTO.setChapters(chapterDTOs);

            return courseDTO;
        }).collect(Collectors.toList());
    }
     */








}
