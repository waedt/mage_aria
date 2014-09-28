// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SweartoSilence extends CardImpl {

    public SweartoSilence(UUID ownerId) {
        super(ownerId, 39, "Swear to Silence", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/2vmfqos.jpg
        Enchant creature
        Aurafaction (If this spell would be countered for having no legal target, you may return it to your hand instead.)
        Enchanted creature can't attack or block and has no abilities.
        */
    }

    public SweartoSilence(final SweartoSilence card) {
        super(card);
    }

    @Override
    public SweartoSilence copy() {
        return new SweartoSilence(this);
    }

}

