// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class NutstoYourMagic extends CardImpl {

    public NutstoYourMagic(UUID ownerId) {
        super(ownerId, 187, "Nuts to Your Magic", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/E4nzKv1.jpg
        If an aura an opponent controls is attached to a creature you control, you may pay G rather than pay Nuts to Your Magic's mana cost.
        Destroy target enchantment. Put a 1/1 green Squirrel creature token onto the battlefield.
        */
    }

    public NutstoYourMagic(final NutstoYourMagic card) {
        super(card);
    }

    @Override
    public NutstoYourMagic copy() {
        return new NutstoYourMagic(this);
    }

}

