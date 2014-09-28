// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SigilofToadandSnake extends CardImpl {

    public SigilofToadandSnake(UUID ownerId) {
        super(ownerId, 196, "Sigil of Toad and Snake", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/1lTS6PU.jpg
        Enchant creature
        Aurafaction (If this spell would be countered for having no legal target, you may return it to your hand instead.)
        Enchanted creature gets +1/+1 and has hexproof and haste.
        */
    }

    public SigilofToadandSnake(final SigilofToadandSnake card) {
        super(card);
    }

    @Override
    public SigilofToadandSnake copy() {
        return new SigilofToadandSnake(this);
    }

}

