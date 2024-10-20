package br.com.padnini.userserviceapi;

import br.com.padnini.userserviceapi.entity.User;
import br.com.padnini.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.enums.ProfileEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class UserServiceApiApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User(null, "vipandini", "admin@padnini.com","12345678", Set.of(ProfileEnum.ROLE_ADMIN)));
    }
}
