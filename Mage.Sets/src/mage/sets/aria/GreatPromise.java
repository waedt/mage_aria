// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class GreatPromise extends CardImpl {

    public GreatPromise(UUID ownerId) {
        super(ownerId, 181, "Great Promise", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/8UL3JJG.jpg
        Enchant creatre
        Aurafaction (If this spell would be countered for having no legal target, you may return it to your hand instead.)
        Enchanted creature gets +2/+2 and has trample.
        */
    }

    public GreatPromise(final GreatPromise card) {
        super(card);
    }

    @Override
    public GreatPromise copy() {
        return new GreatPromise(this);
    }

}

