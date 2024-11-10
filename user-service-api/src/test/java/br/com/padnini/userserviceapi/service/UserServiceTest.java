package br.com.padnini.userserviceapi.service;

import br.com.padnini.userserviceapi.creator.CreatorUtils;
import br.com.padnini.userserviceapi.entity.User;
import br.com.padnini.userserviceapi.mapper.UserMapper;
import br.com.padnini.userserviceapi.repository.UserRepository;
import exceoptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    void whenCallFindByIdWithValidIdThenReturnUserResponse(){
        when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new User()));
        when(userMapper.fromEntity(Mockito.any(User.class))).thenReturn(CreatorUtils.generateMock(UserResponse.class));

        final var response = userService.findById("1");
        assertNotNull(response);
        assertEquals(UserResponse.class, response.getClass());

        verify(userRepository, Mockito.times(1)).findById(Mockito.anyString());
        verify(userMapper, Mockito.times(1)).fromEntity(Mockito.any(User.class));
    }

    @Test
    void whenCallFindByIdWithInvalidIdThenReturnResourceNotFoundException(){
        when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        try {
            userService.findById("1");
        }catch (Exception e){
            assertEquals(ResourceNotFoundException.class, e.getClass());
        }

        verify(userRepository, Mockito.times(1)).findById(Mockito.anyString());
        verify(userMapper, Mockito.times(0)).fromEntity(Mockito.any(User.class));
    }

    @Test
    void whenCallFIndAllThenReturnListOfUserResponse(){
        when(userRepository.findAll()).thenReturn(List.of(new User(),new User()));
        when(userMapper.fromEntity(Mockito.any(User.class))).thenReturn(Mockito.mock(UserResponse.class));

        final var response = userService.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(UserResponse.class, response.get(0).getClass());

        verify(userRepository, Mockito.times(1)).findAll();
        verify(userMapper, Mockito.times(2)).fromEntity(Mockito.any(User.class));

    }

    @Test
    void whenCallSaveThenSuccess(){
        final var request = CreatorUtils.generateMock(CreateUserRequest.class);
        when(userMapper.fromRequest(Mockito.any())).thenReturn(new User());
        when(encoder.encode(Mockito.anyString())).thenReturn("encoded");
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        userService.save(request);
        verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        verify(userRepository, Mockito.times(1)).findByEmail(request.email());

    }

    @Test
    void whenCallSaveWithInvalidEmailThenThrowDataIntegrityViolationException(){
         final var request = CreatorUtils.generateMock(CreateUserRequest.class);
         final  var entity =  CreatorUtils.generateMock(User.class);
         when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(entity));

         try{
             userService.save(request);
         } catch (Exception e) {
             assertEquals(DataIntegrityViolationException.class, e.getClass());
             assertEquals("Email already exists", e.getMessage());
         }

         verify(userRepository, Mockito.times(1)).findByEmail(request.email());
         verify(userMapper, Mockito.times(0)).fromRequest(request);
         verify(userRepository, Mockito.times(1)).findByEmail(request.email());
         verify(encoder, Mockito.times(0)).encode(request.password());
    }

    @Test
    void whenCallUpdateThenWithInvalidIdThenThrowResourceNotFoundException(){
        final var request = CreatorUtils.generateMock(UpdateUserRequest.class);

        when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        try{
            userService.update("1",request);
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals("User not found", e.getMessage());
        }
        verify(userRepository, Mockito.times(1)).findById(Mockito.anyString());
        verify(encoder, Mockito.times(0)).encode(request.password());
        verify(userMapper,times(0)).update(any(),any());
    }

    @Test
    void whenCallUpdateWithInvalidEmailThenThrowDataIntegrityViolationException(){
        final var request = CreatorUtils.generateMock(UpdateUserRequest.class);
        final  var entity =  CreatorUtils.generateMock(User.class);

        when(userRepository.findById(anyString())).thenReturn(Optional.of(entity));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(entity));

        try{
            userService.update("1",request);
        }catch (Exception e) {
            assertEquals(DataIntegrityViolationException.class, e.getClass());
        }

        verify(userRepository, Mockito.times(1)).findById(Mockito.anyString());

    }


}