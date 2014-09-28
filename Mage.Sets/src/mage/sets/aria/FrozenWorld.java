// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class FrozenWorld extends CardImpl {

    public FrozenWorld(UUID ownerId) {
        super(ownerId, 53, "Frozen World", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Ld6iqvb.jpg
        Creatures can't attack.
        Permanents can't untap.
        At the beginning of your upkeep, pay 1U or sacrifice Frozen World.
        */
    }

    public FrozenWorld(final FrozenWorld card) {
        super(card);
    }

    @Override
    public FrozenWorld copy() {
        return new FrozenWorld(this);
    }

}

