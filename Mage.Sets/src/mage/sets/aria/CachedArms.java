// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CachedArms extends CardImpl {

    public CachedArms(UUID ownerId) {
        super(ownerId, 8, "Cached Arms", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/jrS5SSH.jpg
        If a creature with two or less toughness is attacking you, you may pay W rather than pay Cached Arms's mana cost.
        Target creature you control gets +2/+0 and gains first strike and lifelink until end of turn.
        */
    }

    public CachedArms(final CachedArms card) {
        super(card);
    }

    @Override
    public CachedArms copy() {
        return new CachedArms(this);
    }

}

