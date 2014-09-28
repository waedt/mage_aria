// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class PassionatePromise extends CardImpl {

    public PassionatePromise(UUID ownerId) {
        super(ownerId, 142, "Passionate Promise", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/QzBP1nk.jpg
        Enchant creature
        Aurafaction (If this spell would be countered for having no legal target, you may return it to your hand instead.)
        Enchanted creature gets +2/+2 and has haste.
        */
    }

    public PassionatePromise(final PassionatePromise card) {
        super(card);
    }

    @Override
    public PassionatePromise copy() {
        return new PassionatePromise(this);
    }

}

