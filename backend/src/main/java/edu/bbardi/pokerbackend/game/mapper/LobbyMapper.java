package edu.bbardi.pokerbackend.game.mapper;

import edu.bbardi.pokerbackend.game.model.Lobby;
import edu.bbardi.pokerbackend.game.model.PlayingUser;
import edu.bbardi.pokerbackend.game.model.dto.LobbyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LobbyMapper {
    @Mapping(source = "users", target = "users", qualifiedByName = "UserToString")
    @Mapping(source = "status.name", target = "status")
    LobbyDTO toDTO(Lobby lobby);
    @Mapping(source = "users", target = "users", qualifiedByName = "StringToUser")
    @Mapping(source = "status", target = "status.name")
    Lobby fromDTO(LobbyDTO lobbyDTO);

    @Named("StringToUser")
    static Set<PlayingUser> mapStringToUser(Set<String> value) {
        if(value == null)
            return new HashSet<>();
        return value.stream()
                .map(user -> PlayingUser.builder().name(user).build())
                .collect(Collectors.toSet());
    }

    @Named("UserToString")
    static Set<String> mapRoleToString(Set<PlayingUser> value) {
        if(value == null)
            return new HashSet<>();
        return value.stream()
                .map(PlayingUser::getName)
                .collect(Collectors.toSet());
    }
}
