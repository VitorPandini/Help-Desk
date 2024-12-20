package br.com.padnini.userserviceapi.controller.impl;

import br.com.padnini.userserviceapi.creator.CreatorUtils;
import br.com.padnini.userserviceapi.entity.User;
import br.com.padnini.userserviceapi.mapper.UserMapperImpl;
import br.com.padnini.userserviceapi.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.requests.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {


    //Nao preciso subir um servidor.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapperImpl userMapperImpl;

    @Test
    void testFindByIdWithSucess() throws Exception {

        final var entity = CreatorUtils.generateMock(User.class);
        final var userId =userRepository.save(entity).getId();


        mockMvc.perform(get("api/users/{id}",userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()));

        userRepository.deleteById(userId);
    }

    @Test
    void testFindByIdWithNotFoundException() throws Exception {
        mockMvc.perform(get("api/users/{id}","123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());


    }

    @Test
    void testFindAllWithSucess() throws Exception {
        final var entity = CreatorUtils.generateMock(User.class);
        final var entity2 = CreatorUtils.generateMock(User.class);

        userRepository.saveAll(List.of(entity,entity2));

        mockMvc.perform(get("api/users"))
                .andExpect(status().isOk()).andExpect(jsonPath("$").isArray());

        userRepository.deleteAll(List.of(entity2,entity));
    }

    @Test
    void testSaveWithSucess() throws Exception {
        final var validEmail= "test_mail@email.com";
        final var request = CreatorUtils.generateMock(CreateUserRequest.class).withEmail(validEmail);



        mockMvc.perform(post("api/users").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))).andExpect(status().isCreated());

        userRepository.deleteByEmail(validEmail);
    }

    @Test
    void testSaveUserWithConflict() throws Exception {
        final var validEmail= "test_mail@email.com";
        final var request = CreatorUtils.generateMock(CreateUserRequest.class).withEmail(validEmail);
        final var entity = CreatorUtils.generateMock(User.class).withEmail(validEmail);

        userRepository.save(entity);

        mockMvc.perform(post("api/users").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))).andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User already exists"));

        userRepository.deleteById(entity.getId());
    }



    private String toJson(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}