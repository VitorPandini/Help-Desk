package br.com.padnini.userserviceapi.service;

import br.com.padnini.userserviceapi.entity.User;
import br.com.padnini.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById (final String id){
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found or id not valid!"));
    }

}
