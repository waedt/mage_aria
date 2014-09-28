// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SatchelofSand extends CardImpl {

    public SatchelofSand(UUID ownerId) {
        super(ownerId, 215, "Satchel of Sand", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Equipment");

        /*
        Card Text:
        ----------
        http://i.imgur.com/qrL7FqX.jpg
        Whenever you cast an instant or sorcery, equipped creature gets +2/+2 until end of turn.
        Equip 1
        */
    }

    public SatchelofSand(final SatchelofSand card) {
        super(card);
    }

    @Override
    public SatchelofSand copy() {
        return new SatchelofSand(this);
    }

}

