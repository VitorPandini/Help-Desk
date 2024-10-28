package br.com.padnini.userserviceapi.mapper;

import br.com.padnini.userserviceapi.entity.User;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface UserMapper {

//    @Mapping(target = "profiles",source = "profile")
    UserResponse fromEntity(final User entity);

    @Mapping(target = "id",ignore = true)
    User fromRequest(CreateUserRequest request);


    @Mapping(target = "id",ignore = true)
    User update(UpdateUserRequest request, @MappingTarget User entity);
}
