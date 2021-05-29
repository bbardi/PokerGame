package edu.bbardi.pokerbackend.user.mapper;

import edu.bbardi.pokerbackend.user.model.ERole;
import edu.bbardi.pokerbackend.user.model.Role;
import edu.bbardi.pokerbackend.user.model.User;
import edu.bbardi.pokerbackend.user.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "roles", target = "roles", qualifiedByName = "RoleToString")
    UserDTO toDto(User user);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "StringToRole")
    User fromDto(UserDTO user);

    @Named("StringToRole")
    static Set<Role> mapStringToRole(Set<String> value) {
        return value.stream()
                .map(role -> Role.builder().name(ERole.valueOf(role)).build())
                .collect(Collectors.toSet());
    }

    @Named("RoleToString")
    static Set<String> mapRoleToString(Set<Role> value) {
        return value.stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toSet());
    }
}
