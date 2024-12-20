package br.com.padnini.userserviceapi.controller;

import br.com.padnini.userserviceapi.entity.User;
import exceoptions.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "UserController",description = "Controller responsible for user operations")
@RequestMapping("/api/users")
public interface UserController {

    @Operation(summary = "Find user by id")
    @ApiResponses(value =
            {
                    @ApiResponse(responseCode = "200",description = "User found"),
                    @ApiResponse(
                            responseCode ="404",description = "User not found ",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
                    ),
                    @ApiResponse(responseCode ="500",description = "Internal server error")
            })
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
            @Parameter(description = "User id",required = true,example = "556090saas8u0909a")
            @PathVariable("id") final String id);

    @Operation(summary = "Save new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(
                    responseCode ="400",description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            )
    })
    @PostMapping
    ResponseEntity<Void> saveUser( @Valid @RequestBody final  CreateUserRequest request);

    @Operation(summary = "Find all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",description = "Users found",content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(
                    responseCode = "500",description ="Internal server error",content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = StandardError.class)
            )
            )
    })
    @GetMapping
    ResponseEntity<List<UserResponse>> findAll();

    @Operation(summary = "Update user" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "User updated",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404",description = "User not found",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "400",description = "Bad request",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = StandardError.class))),
    })
    @PutMapping("/{id}")
    ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "User id",required = true,example = "556090saas8u0909a")
            @PathVariable("id") final String id, @RequestBody @Valid final UpdateUserRequest request);
}
