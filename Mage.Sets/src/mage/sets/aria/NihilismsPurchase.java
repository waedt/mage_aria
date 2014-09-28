// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class NihilismsPurchase extends CardImpl {

    public NihilismsPurchase(UUID ownerId) {
        super(ownerId, 102, "Nihilism's Purchase", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/fuh0FWH.jpg
        Whenever a card is put into a player's hand, if that player did not draw it, that player puts a card from his or her hand on top of his or her library.
        */
    }

    public NihilismsPurchase(final NihilismsPurchase card) {
        super(card);
    }

    @Override
    public NihilismsPurchase copy() {
        return new NihilismsPurchase(this);
    }

}

