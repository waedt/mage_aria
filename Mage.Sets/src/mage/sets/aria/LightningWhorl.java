// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class LightningWhorl extends CardImpl {

    public LightningWhorl(UUID ownerId) {
        super(ownerId, 139, "Lightning Whorl", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/F9S1fzO.jpg
        Whenever you cast a spell that targets an opponent or a creature an opponent controls, Lightning Whorl deals 1 damage to each opponent.
        */
    }

    public LightningWhorl(final LightningWhorl card) {
        super(card);
    }

    @Override
    public LightningWhorl copy() {
        return new LightningWhorl(this);
    }

}

