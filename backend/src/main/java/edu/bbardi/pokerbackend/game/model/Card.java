package edu.bbardi.pokerbackend.game.model;

import edu.bbardi.pokerbackend.game.model.enums.ECardNumber;
import edu.bbardi.pokerbackend.game.model.enums.ECardSymbol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private ECardNumber cardNumber;
    @Column
    @Enumerated(EnumType.STRING)
    private ECardSymbol cardSymbol;
}
