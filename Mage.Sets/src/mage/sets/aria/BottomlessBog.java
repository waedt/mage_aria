// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BottomlessBog extends CardImpl {

    public BottomlessBog(UUID ownerId) {
        super(ownerId, 88, "Bottomless Bog", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/2C7kZ6s.jpg
        Buyback 1BB (You may pay an additional 1BB as you cast this spell. If you do, put this card into your hand as it resolves.)
        Creatures get -1/-2 until end of turn.
        */
    }

    public BottomlessBog(final BottomlessBog card) {
        super(card);
    }

    @Override
    public BottomlessBog copy() {
        return new BottomlessBog(this);
    }

}

