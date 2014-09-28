// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class PoxParoxysm extends CardImpl {

    public PoxParoxysm(UUID ownerId) {
        super(ownerId, 108, "Pox Paroxysm", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/TLQ6I8O.jpg
        Buypack-Pay 1 life, discard a card, sacrifice a creature, and sacrifice a land.
        Target player loses 1 life and discards a card. If the buyback cost was paid, that player sacrifices a creature and sacrifices a land.
        */
    }

    public PoxParoxysm(final PoxParoxysm card) {
        super(card);
    }

    @Override
    public PoxParoxysm copy() {
        return new PoxParoxysm(this);
    }

}

