// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Groundskeeping extends CardImpl {

    public Groundskeeping(UUID ownerId) {
        super(ownerId, 182, "Groundskeeping", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/LRLCbo6.jpg
        Choose one or both - Return target land card from your graveyard to the battlefield tapped; or search your library for a land card, reveal it, and put it into your hand, then shuffle your library.
        */
    }

    public Groundskeeping(final Groundskeeping card) {
        super(card);
    }

    @Override
    public Groundskeeping copy() {
        return new Groundskeeping(this);
    }

}

