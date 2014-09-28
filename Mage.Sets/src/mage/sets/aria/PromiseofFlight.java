// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class PromiseofFlight extends CardImpl {

    public PromiseofFlight(UUID ownerId) {
        super(ownerId, 67, "Promise of Flight", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/CsnEZAy.jpg
        Enchant creature
        Aurafaction (If this spell would be countered for having no legal target, you may return it to your hand instead.)
        Enchanted creature gets +1/+1 and gains flying.
        */
    }

    public PromiseofFlight(final PromiseofFlight card) {
        super(card);
    }

    @Override
    public PromiseofFlight copy() {
        return new PromiseofFlight(this);
    }

}

