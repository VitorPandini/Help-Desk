package br.com.padnini.userserviceapi.service;

import br.com.padnini.userserviceapi.entity.User;
import br.com.padnini.userserviceapi.mapper.UserMapper;
import br.com.padnini.userserviceapi.repository.UserRepository;
import exceoptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponse findById (final String id){
        return userMapper.fromEntity(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object not found "+ id)));
    }

    public void save(final CreateUserRequest request) {
        userRepository.save(userMapper.fromRequest(request));
    }

    private void verifyIfEmailAlreadyExists(final String email,final String id) {
        userRepository.findByEmail(email).filter(user -> !user.getId().equals(id)).ifPresent(user ->{
            throw new DataIntegrityViolationException("Email already exists");
        });
    }
}
