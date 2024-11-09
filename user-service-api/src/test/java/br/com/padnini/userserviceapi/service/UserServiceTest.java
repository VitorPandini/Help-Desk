package br.com.padnini.userserviceapi.service;

import br.com.padnini.userserviceapi.entity.User;
import br.com.padnini.userserviceapi.mapper.UserMapper;
import br.com.padnini.userserviceapi.repository.UserRepository;
import exceoptions.ResourceNotFoundException;
import models.responses.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        Mockito.when(userMapper.fromEntity(Mockito.any(User.class))).thenReturn(Mockito.mock(UserResponse.class));

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
}