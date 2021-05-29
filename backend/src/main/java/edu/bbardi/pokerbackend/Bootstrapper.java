package edu.bbardi.pokerbackend;

import edu.bbardi.pokerbackend.game.model.*;
import edu.bbardi.pokerbackend.game.model.enums.ECardNumber;
import edu.bbardi.pokerbackend.game.model.enums.ECardSymbol;
import edu.bbardi.pokerbackend.game.model.enums.ELobbyStatus;
import edu.bbardi.pokerbackend.game.repository.CardRepository;
import edu.bbardi.pokerbackend.game.repository.StatusRepository;
import edu.bbardi.pokerbackend.user.model.ERole;
import edu.bbardi.pokerbackend.user.model.Role;
import edu.bbardi.pokerbackend.user.model.User;
import edu.bbardi.pokerbackend.user.repository.RoleRepository;
import edu.bbardi.pokerbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final PasswordEncoder passwordEncoder;
    private final CardRepository cardRepository;
    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrap){
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for(ERole value : ERole.values()){
                roleRepository.save(
                        Role.builder().name(value).build()
                );
            }
            for(ELobbyStatus value : ELobbyStatus.values()){
                statusRepository.save(
                        LobbyStatus.builder().name(value).build()
                );
            }
            for(ECardSymbol symbol : ECardSymbol.values()){
                for(ECardNumber number : ECardNumber.values()){
                    cardRepository.save(
                            Card.builder()
                                    .cardNumber(number)
                                    .cardSymbol(symbol)
                                    .build()
                    );
                }
            }
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(Set.of(roleRepository.findByName(ERole.ADMINISTRATOR)
                            .orElseThrow(() -> new EntityNotFoundException("Role not found")))
                    )
                    .email("admin@admin.com")
                    .balance(5L)
                    .build();
            User player = User.builder()
                    .username("player")
                    .password(passwordEncoder.encode("player"))
                    .roles(Set.of(roleRepository.findByName(ERole.PLAYER)
                            .orElseThrow(() -> new EntityNotFoundException("Role not found")))
                    )
                    .email("player@player.com")
                    .balance(5L)
                    .build();
            userRepository.save(admin);
            userRepository.save(player);
        }
    }
}
