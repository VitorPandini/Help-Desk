package br.com.padnini.userserviceapi.service;

import br.com.padnini.userserviceapi.entity.User;
import br.com.padnini.userserviceapi.mapper.UserMapper;
import br.com.padnini.userserviceapi.repository.UserRepository;
import exceoptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponse findById (final String id){
        return userMapper.fromEntity(find(id));
    }

    public void save(final CreateUserRequest request) {
        verifyIfEmailAlreadyExists(request.email(),null);
        userRepository.save(userMapper.fromRequest(request));
    }

    private void verifyIfEmailAlreadyExists(final String email,final String id) {
        userRepository.findByEmail(email).filter(user -> !user.getId().equals(id)).ifPresent(user ->{
            throw new DataIntegrityViolationException("Email ["+ email +"] already exists");
        });
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(userMapper::fromEntity).toList();
    }

    public UserResponse update(final String id, final UpdateUserRequest request) {
        User entity = find(id);
        verifyIfEmailAlreadyExists(request.email(),id);
        return userMapper.fromEntity(userRepository.save(userMapper.update(request,entity)));
    }

    private User find(final String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object not found "+ id));
    }
}
