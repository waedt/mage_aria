// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class PeerThroughSalvage extends CardImpl {

    public PeerThroughSalvage(UUID ownerId) {
        super(ownerId, 65, "Peer Through Salvage", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/p2QMeCq.jpg
        Reveal the top five cards of your library. You may put an instant or sorcery card from among them into your hand. Put the rest into your graveyard.
        */
    }

    public PeerThroughSalvage(final PeerThroughSalvage card) {
        super(card);
    }

    @Override
    public PeerThroughSalvage copy() {
        return new PeerThroughSalvage(this);
    }

}

