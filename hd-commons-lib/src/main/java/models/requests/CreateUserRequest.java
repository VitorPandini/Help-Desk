package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.With;
import models.enums.ProfileEnum;

import java.util.Set;
@With
public record CreateUserRequest(
        @Schema(description = "User name",example = "Vitor Pandini")
        @NotBlank(message = "Name connot be empty")
        @Size(min = 3, max = 50,message = "Name must contains between 3 and 50 characters")
        String name,
        @Schema(description = "Email",example = "pandini@example.com")
        @Email(message = "Invalid email")
        @NotBlank(message = "Email connot be empty")
        @Size(min = 6,max = 50,message = "Email must contains between 3 and 50 characters")
        String email,
        @Schema(description = "Password",example = "123456")
        @Size(min = 6,max = 50,message = "Password must contain between 3 and 50 characters")
        @NotBlank(message = "Password connot be empty")
        String password,
        @Schema(description = "User profiles",example = "ROLE_ADMIN,ROLE CUSTOMER")

        Set<ProfileEnum> profiles) {
}
