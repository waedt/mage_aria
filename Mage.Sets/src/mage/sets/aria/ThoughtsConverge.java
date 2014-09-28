// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ThoughtsConverge extends CardImpl {

    public ThoughtsConverge(UUID ownerId) {
        super(ownerId, 79, "Thoughts Converge", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/JaMAA5m.jpg
        If an opponent drew a card this turn, you may pay U rather than pay Thoughts Converge's mana cost.
        Discard your hand and choose an opponent. Draw cards equal to the number of cards in that player's hand.
        */
    }

    public ThoughtsConverge(final ThoughtsConverge card) {
        super(card);
    }

    @Override
    public ThoughtsConverge copy() {
        return new ThoughtsConverge(this);
    }

}

