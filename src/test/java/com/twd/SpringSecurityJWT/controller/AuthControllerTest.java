package com.twd.SpringSecurityJWT.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signUp() throws Exception {
        // Créez un objet JSON avec des données invalides
        String invalidUserJson = "{\n" +
                "    \"email\": \"testing@mail.com\",\n" +
                "    \"password\": \"12345\",\n" +
                "    \"address\": \"1234567890°\",\n" +
                "    \"birth_date\": \"55555666\",\n" +
                "    \"role\": \"NOT_Role\"\n" +
                "}";

        // Effectuez la requête POST avec les données invalides
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserJson))
                .andExpect(status().isBadRequest()); // Vérifiez que le statut est 400 Bad Request
    }
}
