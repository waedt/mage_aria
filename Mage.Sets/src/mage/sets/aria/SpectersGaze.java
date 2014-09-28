// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SpectersGaze extends CardImpl {

    public SpectersGaze(UUID ownerId) {
        super(ownerId, 115, "Specter's Gaze", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/kmqfbdF.jpg
        Enchant creature
        Aurafaction
        Enchanted creature gets +2/+2 and has deathtouch.
        Whenever enchanted creature deals damage to a player, that player discards a card.
        */
    }

    public SpectersGaze(final SpectersGaze card) {
        super(card);
    }

    @Override
    public SpectersGaze copy() {
        return new SpectersGaze(this);
    }

}

