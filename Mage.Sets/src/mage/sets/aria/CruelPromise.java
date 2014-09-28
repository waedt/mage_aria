// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CruelPromise extends CardImpl {

    public CruelPromise(UUID ownerId) {
        super(ownerId, 91, "Cruel Promise", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/cc5xvyc.jpg
        Enchant creature
        Aurafaction (If this spell would be countered for having no legal target, you may return it to your hand instead.)
        Enchanted creature gets -1/-1 and can't block.
        */
    }

    public CruelPromise(final CruelPromise card) {
        super(card);
    }

    @Override
    public CruelPromise copy() {
        return new CruelPromise(this);
    }

}

