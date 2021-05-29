export default{
    decodeSpades(number){
        switch(number){
            case "ACE":
                return '🂡';
            case "TWO":
                return '🂢';
            case "THREE":
                return '🂣';
            case "FOUR":
                return '🂤';
            case "FIVE":
                return '🂥';
            case "SIX":
                return '🂦';
            case "SEVEN":
                return '🂧';
            case "EIGHT":
                return '🂨';
            case "NINE":
                return '🂩';
            case "TEN":
                return '🂪';
            case "JACK":
                return '🂫';
            case "QUEEN":
                return '🂭';
            case "KING":
                return '🂮';
        }
    },
    decodeClovers(number){
        switch(number){
            case "ACE":
                return '🃑';
            case "TWO":
                return '🃒';
            case "THREE":
                return '🃓';
            case "FOUR":
                return '🃔';
            case "FIVE":
                return '🃕';
            case "SIX":
                return '🃖';
            case "SEVEN":
                return '🃗';
            case "EIGHT":
                return '🃘';
            case "NINE":
                return '🃙';
            case "TEN":
                return '🃚';
            case "JACK":
                return '🃛';
            case "QUEEN":
                return '🃝';
            case "KING":
                return '🃞';
        }
    },
    decodeHearts(number){
        switch(number){
            case "ACE":
                return '🂱';
            case "TWO":
                return '🂲';
            case "THREE":
                return '🂳';
            case "FOUR":
                return '🂴';
            case "FIVE":
                return '🂵';
            case "SIX":
                return '🂶';
            case "SEVEN":
                return '🂷';
            case "EIGHT":
                return '🂸';
            case "NINE":
                return '🂹';
            case "TEN":
                return '🂺';
            case "JACK":
                return '🂻';
            case "QUEEN":
                return '🂽';
            case "KING":
                return '🂾';
        }
    },
    decodeDiamonds(number){
        switch(number){
            case "ACE":
                return '🃁';
            case "TWO":
                return '🃂';
            case "THREE":
                return '🃃';
            case "FOUR":
                return '🃄';
            case "FIVE":
                return '🃅';
            case "SIX":
                return '🃆';
            case "SEVEN":
                return '🃇';
            case "EIGHT":
                return '🃈';
            case "NINE":
                return '🃉';
            case "TEN":
                return '🃊';
            case "JACK":
                return '🃋';
            case "QUEEN":
                return '🃍';
            case "KING":
                return '🃎';
        }
    },
    decodeCard(card){
        switch(card.cardSymbol){
            case "CLOVERS":
                return this.decodeClovers(card.cardNumber);
            case "SPADES":
                return this.decodeSpades(card.cardNumber);
            case "HEARTS":
                return this.decodeHearts(card.cardNumber);
            case "DIAMONDS":
                return this.decodeDiamonds(card.cardNumber)
        }
    }
}