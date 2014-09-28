// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class KnightlyPromise extends CardImpl {

    public KnightlyPromise(UUID ownerId) {
        super(ownerId, 18, "Knightly Promise", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/KGBc0hA.jpg
        Enchant creature
        Aurafaction (If this spell would be countered for having no legal target, you may return it to your hand instead.)
        When Knightly Promise enters the battlefield, put a 1/1 white Soldier creature token onto the battlefield.
        Enchanted creature gets +1/+1 and has first strike.
        */
    }

    public KnightlyPromise(final KnightlyPromise card) {
        super(card);
    }

    @Override
    public KnightlyPromise copy() {
        return new KnightlyPromise(this);
    }

}

