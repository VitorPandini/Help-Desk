package br.com.padnini.userserviceapi.service;

import br.com.padnini.userserviceapi.creator.CreatorUtils;
import br.com.padnini.userserviceapi.entity.User;
import br.com.padnini.userserviceapi.mapper.UserMapper;
import br.com.padnini.userserviceapi.repository.UserRepository;
import exceoptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new User()));
        Mockito.when(userMapper.fromEntity(Mockito.any(User.class))).thenReturn(CreatorUtils.generateMock(UserResponse.class));

        final var response = userService.findById("1");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(UserResponse.class, response.getClass());

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(userMapper, Mockito.times(1)).fromEntity(Mockito.any(User.class));
    }

    @Test
    void whenCallFindByIdWithInvalidIdThenReturnResourceNotFoundException(){
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        try {
            userService.findById("1");
        }catch (Exception e){
            Assertions.assertEquals(ResourceNotFoundException.class, e.getClass());
        }

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(userMapper, Mockito.times(0)).fromEntity(Mockito.any(User.class));
    }

    @Test
    void whenCallFIndAllThenReturnListOfUserResponse(){
        Mockito.when(userRepository.findAll()).thenReturn(List.of(new User(),new User()));
        Mockito.when(userMapper.fromEntity(Mockito.any(User.class))).thenReturn(Mockito.mock(UserResponse.class));

        final var response = userService.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals(UserResponse.class, response.get(0).getClass());

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verify(userMapper, Mockito.times(2)).fromEntity(Mockito.any(User.class));

    }

    @Test
    void whenCallSaveThenSuccess(){
        final var request = CreatorUtils.generateMock(CreateUserRequest.class);
        Mockito.when(userMapper.fromRequest(Mockito.any())).thenReturn(new User());
        Mockito.when(encoder.encode(Mockito.anyString())).thenReturn("encoded");
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        userService.save(request);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(request.email());

    }


}