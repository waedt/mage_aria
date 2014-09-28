// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class VenomfruitNettles extends CardImpl {

    public VenomfruitNettles(UUID ownerId) {
        super(ownerId, 118, "Venomfruit Nettles", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/kOhLAb7.jpg
        Enchant land
        When enchanted land becomes tapped, its controller adds B to his or her mana pool and loses 2 life.
        */
    }

    public VenomfruitNettles(final VenomfruitNettles card) {
        super(card);
    }

    @Override
    public VenomfruitNettles copy() {
        return new VenomfruitNettles(this);
    }

}

