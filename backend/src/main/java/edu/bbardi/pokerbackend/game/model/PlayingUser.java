package edu.bbardi.pokerbackend.game.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PlayingUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long inGameBalance;

    @Column
    @Builder.Default
    private Boolean ready = false;

    @ManyToOne
    @JoinColumn(name="lobby_id", nullable = false)
    private Lobby currentLobby;
}
