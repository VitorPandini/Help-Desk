package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;

import java.io.Serial;
import java.io.Serializable;

public record AuthenticateRequest(

        @Schema(description = "Email",example = "pandini@example.com")
        @Email(message = "Invalid email")
        @NotBlank(message = "Email connot be empty")
        @Size(min = 6,max = 50,message = "Email must contains between 3 and 50 characters")
        String email,
        @Schema(description = "Password",example = "123456")
        @Size(min = 6,max = 50,message = "Password must contain between 3 and 50 characters")
        @NotBlank(message = "Password connot be empty")
        String password) implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
}
