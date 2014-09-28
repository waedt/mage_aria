// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ReveretheMaster extends CardImpl {

    public ReveretheMaster(UUID ownerId) {
        super(ownerId, 33, "Revere the Master", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/bK4ayU5.jpg
        Enchant creature
        Whenever you cast a spell, tap enchanted creature.
        */
    }

    public ReveretheMaster(final ReveretheMaster card) {
        super(card);
    }

    @Override
    public ReveretheMaster copy() {
        return new ReveretheMaster(this);
    }

}

