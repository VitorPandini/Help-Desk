package br.com.padnini.userserviceapi.controller;

import br.com.padnini.userserviceapi.entity.User;
import exceoptions.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserController",description = "Controller responsible for user operations")
@RequestMapping("/api/users")
public interface UserController {

    @Operation(summary = "Find user by id")
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "200",description = "User found"),
                    @ApiResponse(
                            responseCode ="404",description = "User not found ",
                            content = @Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = StandardError.class))
                    ),
                    @ApiResponse(responseCode ="500",description = "Internal server error")
            })
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
            @Parameter(description = "User id",required = true,example = "556090saas8u0909a")
            @PathVariable("id") final String id);
}
