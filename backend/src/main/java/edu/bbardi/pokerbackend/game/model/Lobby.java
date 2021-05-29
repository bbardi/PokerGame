package edu.bbardi.pokerbackend.game.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 120)
    private String title;

    @Column(nullable = false)
    private Long minimumBalance;

    @ManyToOne
    @JoinColumn(name="status_id",nullable = false)
    private LobbyStatus status;

    @OneToMany(mappedBy = "currentLobby", fetch = FetchType.EAGER)
    @Builder.Default
    private Set<PlayingUser> users = new HashSet<>();

}
