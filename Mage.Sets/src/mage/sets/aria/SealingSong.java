// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SealingSong extends CardImpl {

    public SealingSong(UUID ownerId) {
        super(ownerId, 34, "Sealing Song", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/lgN0J07.jpg
        Buyback 2 (You may pay an additional 2 as you cast this spell. If you do, put this card into your hand as it resolves.)
        Exile target card from a graveyard.
        */
    }

    public SealingSong(final SealingSong card) {
        super(card);
    }

    @Override
    public SealingSong copy() {
        return new SealingSong(this);
    }

}

