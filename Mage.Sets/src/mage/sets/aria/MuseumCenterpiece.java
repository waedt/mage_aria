// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MuseumCenterpiece extends CardImpl {

    public MuseumCenterpiece(UUID ownerId) {
        super(ownerId, 213, "Museum Centerpiece", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "ARI";

        

        /*
        Card Text:
        ----------
        http://i.imgur.com/urR9Nfp.jpg
        Creatures you control have haste.
        2, Sacrifice Museum Centerpiece: Creatures you control gain haste until end of turn and can't be blocked this turn except by two or more creatures.
        */
    }

    public MuseumCenterpiece(final MuseumCenterpiece card) {
        super(card);
    }

    @Override
    public MuseumCenterpiece copy() {
        return new MuseumCenterpiece(this);
    }

}

