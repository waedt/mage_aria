// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class AriVengeanceofAcco extends CardImpl {

    public AriVengeanceofAcco(UUID ownerId) {
        super(ownerId, 206, "Ari, Vengeance of Acco", Rarity.COMMON, new CardType[]{CardType.PLANESWALKER}, "{G}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Ari");
        this.color.setWhite(true);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/owJ1m8m.jpg
        
        0: Until end of turn, Ari becomes a 2/2 Spirit creature that has indestructible and "whenever this creature deals damage, put that many loyalty counters on it." Prevent all damage that would be dealt to it this turn. It's still a planeswalker.
        -X: Target creature gets +X/+X and gains lifelink and indestructible until end of turn.
        -8: You get an emblem with "attacking creatures you control get +1/+1 for each other attacking creature."
        
        */
    }

    public AriVengeanceofAcco(final AriVengeanceofAcco card) {
        super(card);
    }

    @Override
    public AriVengeanceofAcco copy() {
        return new AriVengeanceofAcco(this);
    }

}

