package br.com.padnini.userserviceapi.service;

import br.com.padnini.userserviceapi.entity.User;
import br.com.padnini.userserviceapi.mapper.UserMapper;
import br.com.padnini.userserviceapi.repository.UserRepository;
import exceoptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponse findById (final String id){
        return userMapper.fromEntity(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object not found "+ id)));
    }

}
